package com.csc340team2.mvc.postSubscription;


import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.post.Post;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostSubscriptionService {
    @Autowired
    private PostSubscriptionRepository postSubscriptionRepository;

    public List<PostSubscription> getSubscriptionByUser(Account user) {
        return postSubscriptionRepository.findByUser(user);
    }

    public List<Post> getSubscribedPosts(Long userId) {
        return postSubscriptionRepository.getSubscribedPosts(userId, 25);
    }

    public List<PostSubscription> getSubscriptionByCoach(Account coach) {
        return postSubscriptionRepository.findByCoach(coach);
    }
    public boolean isSubscribed(Account user, Account coach){
        return postSubscriptionRepository.existsByCoachAndUser(coach, user);
    }

    @Transactional
    public void subscribe(Account user, Account coach){
        if(!isSubscribed(user, coach)){
            PostSubscription newSubscription = new PostSubscription();
            newSubscription.setUser(user);
            newSubscription.setCoach(coach);
            postSubscriptionRepository.save(newSubscription);
        }
    }

    @Transactional
    public void unsubscribe(Account user, Account coach){
        if(isSubscribed(user,coach)){
            postSubscriptionRepository.deleteByUserAndCoach(user, coach);
        }
    }
}
