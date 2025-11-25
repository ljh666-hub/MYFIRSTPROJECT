package com.ljh.controller;

import com.ljh.dao.CommentDao;
import com.ljh.exception.BusinessException;
import com.ljh.pojo.Comment;
import com.ljh.pojo.Users;
import com.ljh.service.CommentService;
import com.ljh.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;;

    @RequestMapping("/addComment")
    @ResponseBody
    public Result insertComment(@RequestBody Comment comment, HttpSession session) {
        String user = (String)session.getAttribute("userName");
        Users users = userService.selectByUserName(user);
        Integer userId = users.getUserId();
        comment.setUserId(userId);
        if(comment.getRate()>=0 && comment.getRate()<2){
            comment.setRateName("差评");
        }else if(comment.getRate()>=2 && comment.getRate()<4){
            comment.setRateName("中评");
        }else{
            comment.setRateName("好评");
        }
        if (commentService.insertComment(comment) > 0) {
            redisTemplate.delete(redisTemplate.keys("allComment"));
            redisTemplate.delete(redisTemplate.keys("orders*"));
            return new Result(null,Status.COMMENTADD_SUCCESS,"评论成功");
        }else{
            return new Result(null,Status.COMMENTADD_ERROR,"评论失败");
        }
    }

    @RequestMapping("/findComments")
    @ResponseBody
    public List<Comment> findComments(int commentId) {
        return commentService.selectAllComment(commentId);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Result findAll(int brandId,String rateName) {
        int i = commentDao.selectCommentCount(brandId);
        return new Result(commentService.selectAll(brandId,rateName),i,"");
    }

    @RequestMapping("/findByUserId")
    @ResponseBody
    public List<Comment> findByUserId(HttpSession session) {
        String userName = (String)session.getAttribute("userName");
        Users users = userService.selectByUserName(userName);
        return commentService.selectCommentsByUserId(users.getUserId());
    }

    @RequestMapping("/findAllComment")
    @ResponseBody
    public Result findAllComments(int nowPage,Integer brandId) {
        List<Comment> comments = (List<Comment>)redisTemplate.opsForValue().get("allComment");


        if(brandId != null){
            int i = commentDao.selectCommentCount(brandId);
            if(comments!=null&& !comments.isEmpty()){
                return new Result(comments,i,"");
            }

            comments = commentService.selectAllComments(nowPage);
            redisTemplate.opsForValue().set("allComment",comments);
            return new Result(comments,i,"");
        }
        else{
            if(comments!=null&& !comments.isEmpty()){
                return new Result(comments,0,"");
            }
            comments = commentService.selectAllComments(nowPage);
            redisTemplate.opsForValue().set("allComment",comments);
            return new Result(comments,0,"");
        }

    }

    @RequestMapping("/deleteByCommentId")
    @ResponseBody
    public Result deleteByCommentId(int commentId) {
        if(commentService.deleteCommentByCommentId(commentId)){
            redisTemplate.delete(redisTemplate.keys("allComment"));
            return new Result(null,Status.COMMENTDELETE_SUCCESS,"删除成功");
        }else {
            throw new BusinessException("删除失败，请稍后再试",Status.BUSINESS_ERROR);
        }
    }

}
