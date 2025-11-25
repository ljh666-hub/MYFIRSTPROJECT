package com.ljh.service;

import com.ljh.pojo.Comment;

import java.util.List;

public interface CommentService {
    int insertComment(Comment comment);

    List<Comment> selectAllComment(int commentId);

    List<Comment> selectAll(int brandId,String rateName);

    boolean deleteComment(int userId);

    List<Comment> selectAllComments(int nowPage);

    boolean deleteCommentByCommentId(int commentId);

    List<Comment> selectCommentsByUserId(int userId);

}
