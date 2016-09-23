package com.mini_orm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;


import com.mini_orm.android.DatabaseExcute;
import com.mini_orm.test.StuDao;
import com.mini_orm.test.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener {

	
	StuDao stuDao=new StuDao();
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



		stuDao.setDatabaseexcute(new DatabaseExcute(this, 1));
		stuDao.createTable();
		
	 	this.findViewById(R.id.save).setOnClickListener(this);
		this.findViewById(R.id.drop).setOnClickListener(this);
		this.findViewById(R.id.create).setOnClickListener(this);

		this.findViewById(R.id.delete).setOnClickListener(this);

	 	this.findViewById(R.id.select).setOnClickListener(this);

	 	this.findViewById(R.id.update).setOnClickListener(this);

		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Student student=new Student();
		student.setAge(2);
		student.setStuName("王小明");
		//student.setId(-1);

		switch (v.getId()) {
		case R.id.save:

			stuDao.save(student);
			break;
		case R.id.delete:
			student.setId(2);
			student.setStuName("kkkk");
 			stuDao.deleteAll();
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

			List<Student> list=stuDao.queryAll();
			myBaseAdapter.setList(list);

			break;
			case R.id.drop:
				int i=stuDao.drop();
				myBaseAdapter.setList(new ArrayList());

				if (i!=0) Toast.makeText(this,"表已经删除",Toast.LENGTH_SHORT).show();
 				break;
			case R.id.create:
				 i=stuDao.createTable();

				if (i!=0) Toast.makeText(this,"表已经创建",Toast.LENGTH_SHORT).show();
				break;


			default:
			break;
		}
		
		
		
	}

	 

}
