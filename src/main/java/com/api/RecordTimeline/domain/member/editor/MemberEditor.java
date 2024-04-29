package com.api.RecordTimeline.domain.member.editor;

import com.api.RecordTimeline.domain.member.domain.Interest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public class MemberEditor {

    private final String nickname;
    private final Interest interest;
    private final String password;

    public static MemberEditorBuilder builder() {
        return new MemberEditorBuilder();
    }

    public static class MemberEditorBuilder {
        private String nickname;
        private Interest interest;
        private String password;
        MemberEditorBuilder(){

        }

        public MemberEditorBuilder nickname(final String nickname) {
            if (StringUtils.hasText(nickname)) {
                this.nickname = nickname;
            }
            return this;
        }

        public MemberEditorBuilder interest(final Interest interest) {
            this.interest = interest;
            return this;
        }

        public MemberEditorBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public MemberEditor build() {
            return new MemberEditor(nickname,interest,password);}
    }
}
