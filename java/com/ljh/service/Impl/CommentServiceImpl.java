package com.ljh.service.Impl;

import com.ljh.controller.Status;
import com.ljh.dao.CommentDao;
import com.ljh.exception.BusinessException;
import com.ljh.pojo.Comment;
import com.ljh.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Override
    public int insertComment(Comment comment) {
        if((comment.getCommentDetail()==null|"".equals(comment.getCommentDetail()))&comment.getRate()==0){
                throw  new BusinessException("评分或评价详情不能为空", Status.BUSINESS_ERROR);
        }else{
            if(comment.getRate()==1){
                comment.setRateName("差评");
            } else if (comment.getRate()>=2 && comment.getRate()<=3) {
                comment.setRateName("中评");
            }else{
                comment.setRateName("好评");
            }
            return commentDao.insertComment(comment);
        }
    }

    @Override
    public List<Comment> selectAllComment(int commentId) {
        return commentDao.selectComment(commentId);
    }

    @Override
    public List<Comment> selectAll(int brandId,String rateName) {
        List<Comment> comments = commentDao.selectAll(brandId,rateName);
        for (Comment comment : comments) {
            LocalDateTime localDate = LocalDateTime.ofInstant(comment.getCommentTime().toInstant(), ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            comment.setStringCommentTime(formatter.format(localDate));
        }
        return comments;
    }

    @Override
    public boolean deleteComment(int userId) {
        return commentDao.deleteComment(userId)>0;
    }

    @Override
    public List<Comment> selectAllComments(int nowPage) {
        List<Comment> comments = commentDao.selectAllComment(nowPage);
        for (Comment comment : comments) {
            LocalDateTime localDate = LocalDateTime.ofInstant(comment.getCommentTime().toInstant(), ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            comment.setStringCommentTime(formatter.format(localDate));
        }
        return comments;
    }

    @Override
    public boolean deleteCommentByCommentId(int commentId) {
        return commentDao.deleteCommentByCommentId(commentId)>0;
    }

    @Override
    public List<Comment> selectCommentsByUserId(int userId) {
        List<Comment> comments = commentDao.selectCommentByUserId(userId);
        for (Comment comment : comments) {
            LocalDateTime localDate = LocalDateTime.ofInstant(comment.getCommentTime().toInstant(), ZoneId.systemDefault());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            comment.setStringCommentTime(formatter.format(localDate));
        }
        return comments;
    }


}
