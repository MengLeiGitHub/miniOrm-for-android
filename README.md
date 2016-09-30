
##MiniOrm-for-Android是什么?
MiniOrm-for-android  是一款简单，只能，灵活的android   ORM框架，完全基于对象进行操作。主要帮助android程序员的快速开发。通过反射将查询的数据智能的转换成 Entity 。省去开发人员手动解析的时间。

##功能特点：

* 小巧，类库大小27k
 
* 快速，10000条数据，时长3.6秒
* 使用简单，支持实体类注解方式，除了实体类之外只需创建一个DAO就可以进行操作。

* 支持原声的sql语句操作

* 耦合性低

##结构模型：
![mahua](jiegou.png)

* 类关系图（核心不含有数据库操作部分）：
    纯java结构设计，通过接口的设计，将上层业务和实际操作数据库的部分进行分离，开发者可以在此基础上自行实现mysql。。。等数据的支持。下面是java部分核心设计

##接入方法

####在你项目的 build.gradle 文件里添加如下配置 
<pre><code>
 
  
dependencies {
  
    compile 'com.ml.miniorm:miniorm:1.0.0'
}

 
 </code></pre>


##使用方法：

####  框架初始化：

 <pre><code>
 
 
 public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    	//test.db数据库名称
        //1数据库版本号	
        MiniOrm.init(this,1,"test.db");

    }
}
 
 
 </code></pre>
    



####  实体创建：

 <pre><code>
 
 
      import com.miniorm.android.ColumnType;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.enumtype.Parmary;

@Table(name="student")
public class Student {
   
   @TableID(name="sid",isPrimaryKey=true,defaultVal=0,type=   Parmary.AutoIncrement,columnType= ColumnType.INTEGER)
   private int id ;
   
   @TableColumn(name="stuname",columnType=ColumnType.TEXT)
   private String stuName;
   
   
   @TableColumn(name="age",columnType=ColumnType.INTEGER)
   private int age;


   public int getId() {
      return id;
   }


   public void setId(int id) {
      this.id = id;
   }


   public String getStuName() {
      return stuName;
   }


   public void setStuName(String stuName) {
      this.stuName = stuName;
   }


   public int getAge() {
      return age;
   }


   public void setAge(int age) {
      this.age = age;
   }


   @Override
   public String toString() {
      return "age="+age+"  stuname="+stuName+" id="+id;
   }
}
 
 
 </code></pre>

####注解说明：
* @Table(name="student")设置表名 为 “student”
* @TableID(name="sid",isPrimaryKey=true,defaultVal=0,type= Parmary.AutoIncrement,columnType= ColumnType.INTEGER)
 依次顺序表示 ： 字段名= sid ，字段为表 主键 ，默认数字为   0   ,主键为自动增长，字段类型是
    Integer类型



####含有外键对象实体的创建：

 <pre><code>
 
 import com.miniorm.android.ColumnType;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.enumtype.Parmary;

@Table(name="userTable")
public class Teacher {

   @TableColumn(name="username",columnType= ColumnType.TEXT)
   private  String userName;

   @TableColumn(name="pwd",columnType=ColumnType.TEXT)
   private  String pwd;
   

   @TableID(isPrimaryKey=true,name="userid",defaultVal=0,type= Parmary.AutoIncrement,columnType=ColumnType.INTEGER)
   private int id;
   
   @TableColumn(name="sid",isForeignkey=true,columnType=ColumnType.INTEGER,HierarchicalQueries = true)
   private Student student;


   
   public String getUserName() {
      return userName;
   }
   public void setUserName(String userName) {
      this.userName = userName;
   }
   public String getPwd() {
      return pwd;
   }
   public void setPwd(String pwd) {
      this.pwd = pwd;
   }
   public int getId() {
      return id;
   }
   public void setId(int id) {
      this.id = id;
   }
   public Student getStudent() {
      return student;
   }
   public void setStudent(Student student) {
      this.student = student;
   }


}

 </code></pre>

   ####和普通实体创建唯一不同的地方就是含有外键的对象
*  TableColumn(isForeignkey=true)设置注解的属性为外键
*  HierarchicalQueries = true 表示为外键和主键级联查询，默认此属性为 true 



###创建工具类

<code><pre>
public class TeacherDao  extends androidBaseDao< Teacher> {

   @Override
   public Teacher getQueryEntity() {
      // TODO Auto-generated method stub
      Teacher teacher=new Teacher();
      return teacher;
   }
}


import com.miniorm.android.androidBaseDao;

public class StuDao extends androidBaseDao< Student> {

   @Override
   public Student getQueryEntity() {
      // TODO Auto-generated method stub
      return new Student();
   }

}

</code></pre>

    


#####创建表
<code><pre>

StuDao stuDao=new StuDao();
stuDao.createTable()；
TeacherDao teacherDao=new TeacherDao();
int  i=teacherDao.createTable();

ResultType.SUCCESS==i  表示创建成功
ResultType.FAIL==i  表示创建失败


</code></pre>



#####新增：
<code><pre>
Student student=new Student();
 student.setAge(2);
 student.setStuName("王小明");
Int  id=stuDao.save(student);
if(id!=ResultType.FAIL){
    Id==新增 对象的id
}

</code></pre>

####批量新增：
<code><pre>
ArrayList<Student>  lis2t=new ArrayList<>();
for (int i2=0;i2<10000;i2++){
   Student student1=new Student();
   student1.setStuName("student"+i2);
   student1.setAge(i2);
   lis2t.add(student1);
}
 
stuDao.save(lis2t);

</code></pre>

####删除：
<code><pre>
######Id删除
Student student1=new Student();
student.setId(2);
stuDao.delete(student);
 
stuDao.save(lis2t);

######根据其他属性删除
Student student=new Student();
student.setStuName("kkkk");
    student.setAge(2);
stuDao.delete(student);


#####删除全部
stuDao.deleteAll();
</code></pre>


####更新
<code><pre>

 注意，需指定ID）
student.setId(2);
student.setStuName("kkkk");
stuDao.update(student);


</code></pre>

####查询
<code><pre>

#####按照实体查询
   ######（注意）
   *  指定id，查询出唯一一个 
   *  设置其他参数，非id ，如有数据值返回第一个
    ）

Student student1=    stuDao.QueryByEntity(student);


#####查询全部
List<Student> list=stuDao.queryAll();

#####按照ID查询
stuDao.queryById(1)||stuDao.queryById(“1”)
</code></pre>





##有问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* 邮件:menglei0207@sina.cn
* QQ群: 215233258
* github:https://github.com/MengLeiGitHub/)