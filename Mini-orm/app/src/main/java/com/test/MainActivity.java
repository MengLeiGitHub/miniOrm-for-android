package com.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.miniorm.android.DatabaseExcute;
import com.miniorm.dao.builder.Where;
import com.miniorm.dao.utils.ResultType;
import com.test.test.Card;
import com.test.test.StuDao;
import com.test.test.Student;
import com.test.test.Teacher;
import com.test.test.TeacherDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener {

	
	StuDao stuDao=new StuDao();
	TeacherDao teacherDao=new TeacherDao();
	ListView listview;
	MyBaseAdapter myBaseAdapter;





	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main1);
		listview= (ListView) this.findViewById(R.id.listview);
		myBaseAdapter=new MyBaseAdapter();
		myBaseAdapter.setActivity(this);
		myBaseAdapter.setList(new ArrayList());

		listview.setAdapter(myBaseAdapter);


 	//	stuDao.createTable();
 		//teacherDao.createTable();

	 	this.findViewById(R.id.save).setOnClickListener(this);
		this.findViewById(R.id.drop).setOnClickListener(this);
		this.findViewById(R.id.create).setOnClickListener(this);

		this.findViewById(R.id.delete).setOnClickListener(this);

	 	this.findViewById(R.id.select).setOnClickListener(this);

	 	this.findViewById(R.id.update).setOnClickListener(this);

		this.findViewById(R.id.queryBuilder).setOnClickListener(this);

	}
	int id=1;

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Student student=new Student();
		student.setAge(2);
		student.setStuName("王小明");
		//student.setId(-1);
		Teacher teacher=new Teacher();
		teacher.setUserName("老师 一一");
		teacher.setPwd("123");
  		//student.setId(1);
		teacher.setShengao(140);
 		teacher.setStudent(student);

		switch (v.getId()) {
		case R.id.save:
			int i=0;

/*


			ArrayList<Student>  lis2t=new ArrayList<>();
			for (int i2=0;i2<10000;i2++){
				Student student1=new Student();
				student1.setStuName("student"+i2);
				student1.setAge(i2);
				lis2t.add(student1);
			}
			long time=System.currentTimeMillis();

			stuDao.save(lis2t);

			System.out.println("totalTime=" + (System.currentTimeMillis()-time));*/

			i=stuDao.save(student);

			if(i!=0)
		    teacher.getStudent().setId(i);
			teacher.setSex("女");
			//Student student1=	stuDao.queryById(i);
		//	Log.e("tag", JSON.toJSONString(student1));
		 	teacherDao.save(teacher);





			break;
		case R.id.delete:
			student.setId(2);
			student.setStuName("kkkk");
 			stuDao.delete(student);
			teacherDao.deleteAll();
			myBaseAdapter.setList(new ArrayList());
			break;
		case R.id.update:
			student.setId(2);
			student.setStuName("kkkk");

			stuDao.update(student);

			break;
			
		case R.id.select:

		/*	student=	stuDao.QueryByEntity(student);
			Log.e("tag", student.getStuName() + "   " + student.getAge() + "   " + student.getId());*/
		 	List<Teacher> list=teacherDao.queryAll();

		//List<Student> list=stuDao.queryAll();
			myBaseAdapter.setList(list);
			int id=teacherDao.queryLastInsertId();
			Log.e("tag","id="+id);



			break;
			case R.id.drop:
				i=stuDao.drop();
				myBaseAdapter.setList(new ArrayList());

				if (i!=0) Toast.makeText(this,"表已经删除",Toast.LENGTH_SHORT).show();
 				break;
			case R.id.create:
				// 	i=stuDao.createTable();
					i=teacherDao.createTable();
				if (i!=0) Toast.makeText(this,"表已经创建",Toast.LENGTH_SHORT).show();
				break;

			case R.id.queryBuilder:
				int lastid=teacherDao.queryLastInsertId();
 			 //  List list1= teacherDao.getQueryBuilder().callQuery().queryAll().where(Where.handle().orderBy("username").limit(10, lastid-10)).executeQueryList();
			//	List list1=teacherDao.executeQueryList("select * from  userTable where ");
				String table=teacherDao.getReflexEntity().getTableEntity().getTableName();
				String  column=teacherDao.getReflexEntity().getTableIdEntity().getColumnName();
				List	  list1= 	teacherDao.getQueryBuilder().callQuery().queryAll().where(Where.handle().and(column,"<=",lastid).and(column,">",lastid-10)).executeQueryList();
				myBaseAdapter.setList(list1);

				break;
			default:



			break;
		}
		
		
		
	}

	 

}
