package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.Role;
import cameo.impianto_balneare.Entity.Token;
import cameo.impianto_balneare.Entity.User;
import cameo.impianto_balneare.Repository.TokenRepository;
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
        var token = new Token(user);
        tokenRepository.save(token);
        return token.getId().toString();
    }

    public void deleteToken(String token) {
        tokenRepository.deleteById(UUID.fromString(token));
    }
}
