package pl.kamilnowak.flatrentalmanagementsystem.exception;

public class TokenIsTooOldException extends Exception{
    public TokenIsTooOldException(String tokenName) {
        super(tokenName + " is too old");
    }
}
