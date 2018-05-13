//package ru.vsu.netcracker.parking.backend.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.security.authentication.encoding.PasswordEncoder;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.codec.Hex;
//import org.springframework.security.crypto.codec.Utf8;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.util.EncodingUtils;
//import org.springframework.stereotype.Service;
//import ru.vsu.netcracker.parking.backend.dao.ObjectsDAO;
//import ru.vsu.netcracker.parking.backend.models.Obj;
//
//import java.util.List;
//
//@Service
//public class UsersService implements UserDetailsService {
//
//    private final long ADMIN = 1L;
//    private final long OWNER = 2L;
//    private final long USER = 3L;
//    private final long REST_API_USER = 4L;
//
//    private final long PASSWORD_ATTRIBUTE_ID = 203L;
//    private final long ROLE_ATTRIBUTE_ID = 200L;
//
//    private ObjectsDAO dao;
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public UsersService(ObjectsDAO objectsDAO, PasswordEncoder passwordEncoder) {
//        this.dao = objectsDAO;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(final String userName) throws UsernameNotFoundException {
//
//        Obj obj = dao.getObjByUserName(userName);
//        if (obj == null) {
//            throw new UsernameNotFoundException("No user found with username: " + userName);
//        }
//
//        String password = obj.getValues().get(PASSWORD_ATTRIBUTE_ID);
//
//        long roleId = obj.getReferences().get(ROLE_ATTRIBUTE_ID);
//        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.NO_AUTHORITIES;
//
//        if (roleId == ADMIN) {
//            grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_OWNER", "ROLE_USER");
//        } else if (roleId == OWNER) {
//            grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_OWNER", "ROLE_USER");
//        } else if (roleId == USER) {
//            grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_USER");
//        } else if (roleId == REST_API_USER) {
//            grantedAuthorities = AuthorityUtils.createAuthorityList("ROLE_REST_API_USER");
//        }
///*
//        CustomSHA256PasswordEncoder customSHA256PasswordEncoder = new CustomSHA256PasswordEncoder();
//
//        //login form
//        String username = "someUserName";
//        String password = "somePassword";
//
//        //database
//        String databaseUsername = "someUserName";
//        String databaseEncodedPassword = customSHA256PasswordEncoder.encode(password, userName.getBytes());
//
//        //comparison
//        byte[] salt = userName.getBytes();
//
//        String encodedPassword1 = customSHA256PasswordEncoder.encode(password, salt);
//        byte[] digestedPassword1 = customSHA256PasswordEncoder.digest(password, salt);
//
//        byte[] digestedPassword2 = customSHA256PasswordEncoder.decode(databaseEncodedPassword);
//        byte[] digestedPassword3 = customSHA256PasswordEncoder.digest(password, salt);
//
//
//        boolean equal1 = matches(databaseEncodedPassword.getBytes(), encodedPassword1.getBytes());
//        boolean equal2 = matches(decode(databaseEncodedPassword), digestedPassword1);
//        boolean equal3 = matches(digestedPassword2, digestedPassword3);
//
//
//        //String encodedPassword1 = "a9927b32d8937b94a3c33b54e969e826753f739b388f0be5a54830887fa93789801e66d8506f3549";
//        //String encodedPassword0 = "7e54a21d6b98b526bf43edeedebcf0d2620737c690a5c71d1541deed95c5f73d6640d2d4b735b75f";
//
//        //String encodedPassword1 = passwordEncoder.encode("password");
//
//
//        Digester digester =  new Digester("SHA-256", 1024);
//
//        //encoded password stored in database
//        String encodedPassword0 = "ffbc1402f4cd8d11f2e13ec1849965d031b9757351e0bfa75b76e3ddfb7f4a06eb72ff7fde651596";
//        byte[] digested = decode(encodedPassword0);
//        byte[] salt0 = EncodingUtils.subArray(digested, 0, 8);
//
//        String rawPw = "password";
//        String salt20 = "qwertyuiopqwertyuiop";
//        String salt10 = "qwertyuiop";
//
//        byte[] digest = digest(rawPw, salt20.getBytes(), digester);
//        byte[] digest1 = digest(rawPw, salt10.getBytes(), digester);
//
//        //password from login form
//        String rawPassword = "password";
//        byte[] digested10 = digest("password", salt0, digester);
//
//        boolean passwordsEqual = matches(digested, digested10);
//
//
//        String encodedPassword_1 = "a9927b32d8937b94a3c33b54e969e826753f739b388f0be5a54830887fa93789801e66d8506f3549";
//        String encodedPassword_2 = "7e54a21d6b98b526bf43edeedebcf0d2620737c690a5c71d1541deed95c5f73d6640d2d4b735b75f";
//*/
///*
//        byte[] digested1 = decode(encodedPassword1);
//        byte[] digested2 = decode(encodedPassword2);
//        byte[] salt1 = EncodingUtils.subArray(digested1, 0, 8);
//        byte[] salt2 = EncodingUtils.subArray(digested2, 0, 8);
//
//        byte[] digested3 = digest("password", salt1, digester);
//        byte[] digested4 = digest("password", salt1, digester);
//        char[] str3 = Hex.encode(digested3);
//        char[] str4 = Hex.encode(digested4);
//
//        boolean flag = matches(digested1, digested3);
//*/
//        return new User(userName, password, grantedAuthorities);
//    }
//}
