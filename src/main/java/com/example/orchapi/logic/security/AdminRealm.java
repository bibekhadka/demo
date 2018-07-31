package com.example.orchapi.logic.security;

import com.example.orchapi.schema.admin.Admin;
import com.example.orchapi.schema.admin.AdminRoleLink;
import com.example.orchapi.schema.admin.RolePermissionLink;
import com.example.orchapi.service.admin.AdminRoleLinkService;
import com.example.orchapi.service.admin.AdminService;
import com.example.orchapi.service.admin.RolePermissionLinkService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminRealm extends JdbcRealm {

    @Autowired
    AdminService adminService;
    @Autowired
    AdminRoleLinkService adminRoleLinkService;
    @Autowired
    RolePermissionLinkService rolePermissionLinkService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();

        // Null username is invalid
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }

        Connection conn = null;
        SimpleAuthenticationInfo info = null;
        try {

            String password = null;
            String salt = null;
            switch (saltStyle) {
                case NO_SALT:
                    password = getPasswordForUser(conn, username)[0];
                    break;
                case CRYPT:
                    // TODO: separate password and hash from getPasswordForUser[0]
                    throw new ConfigurationException("Not implemented yet");
                //break;
                case COLUMN:
                    String[] queryResults = getPasswordForUser(conn, username);
                    password = queryResults[0];
                    salt = queryResults[1];
                    break;
                case EXTERNAL:
                    password = getPasswordForUser(conn, username)[0];
                    salt = getSaltForUser(username);
            }

            if (password == null) {
                throw new UnknownAccountException("No account found for user [" + username + "]");
            }

            info = new SimpleAuthenticationInfo(username, password.toCharArray(), getName());

            if (salt != null) {
                info.setCredentialsSalt(ByteSource.Util.bytes(salt));
            }

        } catch (SQLException e) {
            final String message = "There was a SQL error while authenticating user [" + username + "]";
            throw new AuthenticationException(message, e);
        }

        return info;

    }

    private String[] getPasswordForUser(Connection conn, String username) throws SQLException {
        String[] result;
        boolean returningSeparatedSalt = false;
        switch (saltStyle) {
            case NO_SALT:
            case CRYPT:
            case EXTERNAL:
                result = new String[1];
                break;
            default:
                result = new String[2];
                returningSeparatedSalt = true;
        }

        Admin admin = adminService.getByEmail(username);
        result[0] = admin.getPassword();
        if (returningSeparatedSalt) {
            result[1] = admin.getSalt();
        }

        return result;
    }

    @Override
    protected Set<String> getRoleNamesForUser(Connection conn, String username) throws SQLException {
        Set<String> roleNames = new LinkedHashSet<String>();

        List<AdminRoleLink> adminRoleList = adminRoleLinkService.getByAdminEmail(username);
        Iterator<AdminRoleLink> adminRoleListIterator = adminRoleList.iterator();
        while (adminRoleListIterator.hasNext()) {

            String roleName = adminRoleListIterator.next().getRole().getName();
            if (roleName != null) {
                roleNames.add(roleName);
            }
        }
        return roleNames;
    }

    @Override
    protected Set<String> getPermissions(Connection conn, String username, Collection<String> roleNames) throws SQLException {

        Set<String> permissions = new LinkedHashSet<String>();

        for (String roleName : roleNames) {

            List<RolePermissionLink> rolePermissionLinkList = rolePermissionLinkService.getByRoleName(roleName);
            Iterator<RolePermissionLink> rolePermissionLinkListIterator = rolePermissionLinkList.iterator();

            while (rolePermissionLinkListIterator.hasNext()) {

                String permissionString = rolePermissionLinkListIterator.next().getPermission().getName();

                permissions.add(permissionString);
            }

        }

        return permissions;
    }

}
