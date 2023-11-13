package com.example.restauth.service.clients;

import com.example.restauth.dto.Comment;
import com.example.restauth.dto.Post;
import com.example.restauth.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserClientServiceImpl implements UserClientService {

  public static final String POSTS = "/posts";
  public static final String USERS = "/users";
  public static final String COMMENTS = "/comments";
  private final RestTemplate restTemplate;

  @Value("${user.client.base.url}")
  private String userClientBaseUrl;

  /**
   * Get all users
   *
   * @return {@link List<User>} users list
   */
  @Override
  public List<User> getAllUsers() {
    final URI getUsersURI =
        UriComponentsBuilder.fromHttpUrl(userClientBaseUrl).path(USERS).build().toUri();
    final User[] userList = restTemplate.getForObject(getUsersURI, User[].class);
    return Arrays.asList(userList);
  }

  /**
   * Get all posts
   *
   * @return {@link List<Post>} post list
   */
  @Override
  public List<Post> getAllPosts() {
    final URI getPostsURI =
        UriComponentsBuilder.fromHttpUrl(userClientBaseUrl).path(POSTS).build().toUri();
    final Post[] posts = restTemplate.getForObject(getPostsURI, Post[].class);
    return Arrays.asList(posts);
  }

  /**
   * Get all comments
   *
   * @return {@link List<Comment>} comments list
   */
  @Override
  public List<Comment> getAllComments() {
    final URI getCommentsURI =
        UriComponentsBuilder.fromHttpUrl(userClientBaseUrl).path(COMMENTS).build().toUri();
    final Comment[] comments = restTemplate.getForObject(getCommentsURI, Comment[].class);
    return Arrays.asList(comments);
  }

  /**
   * Get posts of a user
   *
   * @param userId given user id
   * @return {@link List<Post>} posts list
   */
  @Override
  public List<Post> getUserPosts(final int userId) {
    final URI getUserPostsURI =
        UriComponentsBuilder.fromHttpUrl(userClientBaseUrl)
            .path(USERS)
            .path("/" + userId)
            .path(POSTS)
            .build()
            .toUri();
    final Post[] posts = restTemplate.getForObject(getUserPostsURI, Post[].class);
    return Arrays.asList(posts);
  }

  /**
   * Get comments of a post
   *
   * @param postId given post id
   * @return {@link List<Comment>} comment list
   */
  @Override
  public List<Comment> getAllCommentsOfPost(final int postId) {
    final URI getPostCommentsURI =
        UriComponentsBuilder.fromHttpUrl(userClientBaseUrl)
            .path(POSTS)
            .path("/" + postId)
            .path(COMMENTS)
            .build()
            .toUri();
    final Comment[] comments = restTemplate.getForObject(getPostCommentsURI, Comment[].class);
    return Arrays.asList(comments);
  }
}
