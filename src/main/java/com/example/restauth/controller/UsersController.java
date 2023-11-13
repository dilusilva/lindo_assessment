package com.example.restauth.controller;

import com.example.restauth.dto.Comment;
import com.example.restauth.dto.Post;
import com.example.restauth.dto.User;
import com.example.restauth.service.clients.UserClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsersController {

  private final UserClientService userClientService;

  /**
   * Get all users
   *
   * @return a {@link List<User>} user list
   */
  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> userList = userClientService.getAllUsers();
    return new ResponseEntity<>(userList, HttpStatus.OK);
  }

  /**
   * Get all posts
   *
   * @return a {@link List<Post>} post list
   */
  @GetMapping("/posts")
  public final ResponseEntity<List<Post>> getAllPosts() {
    List<Post> postList = userClientService.getAllPosts();
    return new ResponseEntity<>(postList, HttpStatus.OK);
  }

  /**
   * Get all comments
   *
   * @return a {@link List<Comment>} comments list
   */
  @GetMapping("/comments")
  public final ResponseEntity<List<Comment>> getAllComments() {
    List<Comment> commentList = userClientService.getAllComments();
    return new ResponseEntity<>(commentList, HttpStatus.OK);
  }

  /**
   * Get posts of a user
   *
   * @param userId given user id
   * @return a {@link List<Post>} post list
   */
  @GetMapping("/user/{user_id}/posts")
  public final ResponseEntity<List<Post>> getPostsOfUser(
      @PathVariable("user_id") final int userId) {
    List<Post> postList = userClientService.getUserPosts(userId);

    return new ResponseEntity<>(postList, HttpStatus.OK);
  }

  /**
   * Get comments of a post
   *
   * @param postId given post id
   * @return a {@link List<Comment>} comments list
   */
  @GetMapping("/post/{post_id}/comments")
  public final ResponseEntity<List<Comment>> getCommentsOfPosts(
      @PathVariable("post_id") final int postId) {
    List<Comment> commentList = userClientService.getAllCommentsOfPost(postId);
    return new ResponseEntity<>(commentList, HttpStatus.OK);
  }
}
