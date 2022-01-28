package cameo.impianto_balneare.service;

import cameo.impianto_balneare.entity.Role;
import cameo.impianto_balneare.entity.Token;
import cameo.impianto_balneare.entity.User;
import cameo.impianto_balneare.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public boolean checkToken(String tokenId, Role role) {
        var user = getUserFromUUID(tokenId);
        if (user == null) return false;
        return user.getRole().equals(role);
    }

    public User getUserFromUUID(String tokenId) {
        if (tokenId == null) return null;
        var token = tokenRepository.findAll().stream().filter(t -> t.getId().equals(UUID.fromString(tokenId))).findFirst();
        return token.map(Token::getUser).orElse(null);
    }

    public String createToken(User user) {
        deleteToken(user);
        var token = new Token(user);
        tokenRepository.save(token);
        return token.getId().toString();
    }

    public void deleteToken(String token) {
        var user = getUserFromUUID(token);
        deleteToken(user);
    }
    private void deleteToken(User user){
        if (user == null) return;
        var tokenList = tokenRepository.findAll()
                .stream().filter(t -> t.getUser().equals(user))
                .collect(Collectors.toList());
        if (tokenList.isEmpty()) return;
        tokenRepository.deleteAll(tokenList);
    }
}
