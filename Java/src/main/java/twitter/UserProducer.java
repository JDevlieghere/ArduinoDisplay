package twitter;

import org.slf4j.Logger;
import twitter4j.*;

import java.util.concurrent.BlockingQueue;

public class UserProducer extends Producer {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(UserProducer.class);

    public UserProducer(BlockingQueue queue) {
        super(queue);
    }

    public void start(){
        this.getTwitterStream().addListener(listener);
        this.getTwitterStream().user();
    }

    private UserStreamListener listener = new UserStreamListener(){

        @Override
        public void onException(Exception e) {
            log.error(e.toString());
        }

        @Override
        public void onStatus(Status status) {
            getQueue().offer(status);
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
            log.warn(stallWarning.toString());
            log.info("Queue size: " + getQueue().size());
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
