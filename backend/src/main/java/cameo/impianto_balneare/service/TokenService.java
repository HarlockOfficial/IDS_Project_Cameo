package cameo.impianto_balneare.service;

import cameo.impianto_balneare.entity.Role;
import cameo.impianto_balneare.entity.Token;
import cameo.impianto_balneare.entity.User;
import cameo.impianto_balneare.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        var token = tokenRepository.findById(UUID.fromString(tokenId));
        return token.map(Token::getUser).orElse(null);
    }

    public String createToken(User user) {
        tokenRepository.findAll().stream().filter(token -> token.getUser().getId().equals(user.getId())).forEach(tokenRepository::delete);
        var token = new Token(user);
        tokenRepository.save(token);
        return token.getId().toString();
    }

    public void deleteToken(String token) {
        tokenRepository.deleteById(UUID.fromString(token));
    }
}
