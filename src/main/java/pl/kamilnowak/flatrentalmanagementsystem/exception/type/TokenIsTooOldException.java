package pl.kamilnowak.flatrentalmanagementsystem.exception.type;

public class TokenIsTooOldException extends Exception{
    public TokenIsTooOldException(String tokenName) {
        super(tokenName + " is too old");
    }
}
