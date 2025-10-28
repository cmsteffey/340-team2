package com.csc340team2.mvc.post;

import com.csc340team2.mvc.account.Account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(Account author, String title, String content){
        Instant now = Instant.now();
        Post post = new Post();
        post.setAuthor(author);
        post.setTitle(title);
        post.setContent(content);
        post.setCreatedAt(now);
        postRepository.save(post);
        return post;
    }

    public Optional<Post> getPostById(Long id){
        return postRepository.findById(id);
    }

    public List<Post> getAllPostsMadeBy(Account author){
        return postRepository.getAllByAuthor(author);
    }
}
