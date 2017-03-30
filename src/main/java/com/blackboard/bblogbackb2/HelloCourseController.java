package com.blackboard.logbackcourseuser;
//package com.blackboard.bblogbackb2;

import java.util.LinkedList;
import java.util.List;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.security.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import blackboard.data.course.Course;
import blackboard.data.course.CourseMembership;
import blackboard.data.user.User;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.course.CourseMembershipDbLoader;
import blackboard.persist.user.UserDbLoader;
import blackboard.platform.spring.web.annotations.IdParam;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Controller
public class HelloCourseController
{

	private static final Logger logger = LoggerFactory.getLogger(HelloCourseController.class);

  // Annotates a variable so that the Spring container will
  // attempt to wire the reference for you automatically.
  @Autowired
  private CourseMembershipDbLoader _membershipLoader;

  @Autowired
  private UserDbLoader _userLoader;

  @RequestMapping( "/course_users" )
  // @IdParam will take the string in the request parameter listed and use it as an Id object
  // to look up the object based on the type.   In this case, it will convert the string to
  // an Id and look up the Course based on the Id.
  public ModelAndView listCourseUsers( @IdParam( "cid" ) Course course )
    throws KeyNotFoundException, PersistenceException
  {
		logger.info("ENTER HelloCourseController:listCourseUsers()");
		String courseId = course.getCourseId();
		if (courseId == null)
			courseId = "null";
		if ("".equals(courseId))
			courseId = "empty string";
		logger.info("HelloCourseController:course.getCourseId():" + courseId);

    ModelAndView mv = new ModelAndView( "course_users" );
    mv.addObject( "course", course );

    // Load some data and put it in the model
    List<CourseMembership> members = CourseMembershipDbLoader.Default.getInstance().loadByCourseId( course.getId() );
    List<User> users = new LinkedList<User>();
    for ( CourseMembership member : members )
    {
      users.add( _userLoader.loadById( member.getUserId() ) );
    }
    mv.addObject( "users", users );

    if (users.size() > 0){ // demonstrate the use of loadByCourseAndUserId
      CourseMembership member2 = CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId( course.getId(), users.get(0).getId() );
      User user2 = null;

        user2 = _userLoader.loadById( member2.getUserId() );

      mv.addObject("user2", user2);
    }// end demonstrate the use of loadByCourseAndUserId

    /* Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
    String formattedDate = dateFormat.format(date);
    mv.addObject("serverTime", formattedDate ); */

    // Test loadByCourseAndUserId for every user.
		List <User> userslbcuid = new LinkedList<User>();
		for (User aUser : users){
			CourseMembership aMember = CourseMembershipDbLoader.Default.getInstance().loadByCourseAndUserId( course.getId(), aUser.getId() );
			userslbcuid.add(_userLoader.loadById( aMember.getUserId() ) );
		}
		mv.addObject("userslbcuid", userslbcuid);

		logger.info("EXIT HelloCourseController:listCourseUsers()");
    return mv;
  }

}
