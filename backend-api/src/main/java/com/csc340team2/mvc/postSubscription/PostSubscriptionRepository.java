package com.csc340team2.mvc.postSubscription;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostSubscriptionRepository extends JpaRepository<PostSubscription, Long> {
    List<PostSubscription> findByCoach(Account coach);
    List<PostSubscription> findByUser(Account user);
    boolean existsByCoachAndUser(Account coach, Account user);
    void deleteByCoachAndUser(Account coach, Account user);

    @Query(value = "select post.title, post.content, account.username, post.created_at from postsubscription ps join post on post.author_id = ps.coach_id join account on post.author_id = account.id where ps.customer_id = :id order by post.created_at desc limit :limit;", nativeQuery = true)
    List<Post> getSubscribedPosts(@Param("id") Long id, @Param("limit") int limit);
    //    select post.title, post.content, account.username, post.created_at from postsubscription ps join post on post.author_id = ps.coach_id join account on post.author_id = account.id where ps.customer_id = 1
    //    order by post.created_at desc limit 10;

}
