package com.example.restauth.service.clients;

import com.example.restauth.dto.Comment;
import com.example.restauth.dto.Post;
import com.example.restauth.dto.User;

import java.util.List;

public interface UserClientService {

  List<User> getAllUsers();

  List<Post> getAllPosts();

  List<Comment> getAllComments();

  List<Post> getUserPosts(int userId);

  List<Comment> getAllCommentsOfPost(int postId);
}
