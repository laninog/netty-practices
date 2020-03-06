package org.training.messages;

public class Reply extends Message {

    private ReplyBody body;

    public Reply(ReplyBody body) {
        super();
        setType(MessageType.REPLY);
        this.body = body;
    }

    public ReplyBody getBody() {
        return body;
    }

    public static class ReplyBody {
        private String body;

        public ReplyBody(String body) {
            this.body = body;
        }

        public String getBody() {
            return body;
        }

        @Override
        public String toString() {
            return "ReplyBody{" +
                    "body='" + body + '\'' +
                    '}';
        }
    }

    public static class ReplyBodyServer extends ReplyBody {
        public ReplyBodyServer(String body) {
            super(body);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public static class ReplyBodyClient extends ReplyBody {
        public ReplyBodyClient(String body) {
            super(body);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

}
