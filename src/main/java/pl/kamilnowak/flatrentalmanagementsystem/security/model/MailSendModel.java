package pl.kamilnowak.flatrentalmanagementsystem.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailSendModel {
    private boolean isSend;

    public MailSendModel() {
    }

    @Builder
    public MailSendModel(boolean isSend) {
        this.isSend = isSend;
    }
}
