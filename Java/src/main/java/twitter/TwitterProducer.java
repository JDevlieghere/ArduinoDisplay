package twitter;

import twitter4j.*;

import java.util.concurrent.BlockingQueue;

public class TwitterProducer {

    private final BlockingQueue statusQueue;
    private final TwitterStream twitterStream;

    public TwitterProducer(BlockingQueue statusQueue) {
        this.statusQueue = statusQueue;
        this.twitterStream = new TwitterStreamFactory().getInstance();
    }

    public void start(){
        this.twitterStream.addListener(listener);
        this.twitterStream.user();
    }

    UserStreamListener listener = new UserStreamListener(){

        @Override
        public void onException(Exception e) {

        }

        @Override
        public void onStatus(Status status) {
            statusQueue.offer(status);
        }

        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

        }

        @Override
        public void onTrackLimitationNotice(int i) {

        }

        @Override
        public void onScrubGeo(long l, long l1) {

        }

        @Override
        public void onStallWarning(StallWarning stallWarning) {

        }

        @Override
        public void onDeletionNotice(long l, long l1) {

        }

        @Override
        public void onFriendList(long[] longs) {

        }

        @Override
        public void onFavorite(User user, User user1, Status status) {

        }

        @Override
        public void onUnfavorite(User user, User user1, Status status) {

        }

        @Override
        public void onFollow(User user, User user1) {

        }

        @Override
        public void onUnfollow(User user, User user1) {

        }

        @Override
        public void onDirectMessage(DirectMessage directMessage) {

        }

        @Override
        public void onUserListMemberAddition(User user, User user1, UserList userList) {

        }

        @Override
        public void onUserListMemberDeletion(User user, User user1, UserList userList) {

        }

        @Override
        public void onUserListSubscription(User user, User user1, UserList userList) {

        }

        @Override
        public void onUserListUnsubscription(User user, User user1, UserList userList) {

        }

        @Override
        public void onUserListCreation(User user, UserList userList) {

        }

        @Override
        public void onUserListUpdate(User user, UserList userList) {

        }

        @Override
        public void onUserListDeletion(User user, UserList userList) {

        }

        @Override
        public void onUserProfileUpdate(User user) {

        }

        @Override
        public void onBlock(User user, User user1) {

        }

        @Override
        public void onUnblock(User user, User user1) {

        }
    };

}
