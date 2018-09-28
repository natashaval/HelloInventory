package hello.inven.helloinven.service;

// http://www.doubleh.ie/index.php/2016/09/09/how-to-save-db-user-entity-in-spring-security-authentication-object/
//@Service
//@Transactional
public class InternalUserDetailService {// implements UserDetailsService {
//    @Autowired
//    private final MyUserRepository userRepository;
//
//    @Override
//    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
//        MyUser user = userRepository.findByUsername(username);
//        if (user == null){
//            throw new UsernameNotFoundException("Unknown MyUser");
//        }
//
//        boolean enabled = true;
//        boolean accountNonExpired = true;
//        boolean credentialsNotExpired = true;
//        boolean accountNonLocked = true;
//
//        UserPrincipal principal = new UserPrincipal(user,
//                enabled, accountNonExpired, credentialsNotExpired, accountNonLocked,
//                getAuthorities(user.getRole()));
//        return principal;
//
//        private List<GrantedAuthority> getAuthorities(List<String> roles) {
//            return roles.stream()
//                    .map(r -> new SimpleGrantedAuthority(r))
//                    .collect(Collectors.toList());
//
//        }
//    }
//

}
