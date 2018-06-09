# MiniOrm2
在原来1版的基础上增加了关系映射，优化了插入、查询，效率更高



##使用方法

####如何接入？
 
 ```java
	compile 'com.github.mengleigithub:miniorm-core:2.0.5'

	annotationProcessor 'com.github.mengleigithub:miniorm-compiler:1.0.2'

 ```




 *[基本使用方法,请参考这里](https://github.com/MengLeiGitHub/miniOrm-for-android/blob/master/README.md)

  
 #####一对一
```java

     @Table(name="userTable")
    public class Teacher {

    @TableColumn(name="username",columnType= ColumnType.TEXT)
	private  String userName;

	@TableColumn(name="pwd",columnType=ColumnType.TEXT)
	private  String pwd;
	

	@TableID(isPrimaryKey=true,name="userid",defaultVal=0,type= Parmary.CUSTOM,columnType=ColumnType.INTEGER)
	private long id;
	
	@TableColumn(name="sid",isForeignkey=true,columnType=ColumnType.INTEGER,HierarchicalQueries = true)
	private Student student;

	@TableColumn(name="sex",columnType=ColumnType.VARCHAR)
	private  String sex;
	@TableColumn(name="shengao",columnType=ColumnType.INTEGER)
	private  int shengao;

	@TableColumn(name="isGril",columnType= ColumnType.BOOLEAN,IgnoreBooleanParam = true)
	private  boolean isGril;


	private ArrayList<Student> students;




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
	public long getId() {
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getShengao() {
		return shengao;
	}

	public void setShengao(int shengao) {
		this.shengao = shengao;
	}

	public boolean isGril() {
		return isGril;
	}

	public void setIsGril(boolean isGril) {
		this.isGril = isGril;
	}
}
 
```
```java

@Table(name="student")
public class Student {
    
	@TableID(name="sid",isPrimaryKey=true,defaultVal=0,type= Parmary.AutoIncrement,columnType= ColumnType.INTEGER)
	private int id ;
	
	@TableColumn(name="stuname",columnType=ColumnType.TEXT)
	private String stuName;
	
	
	@TableColumn(name="age",columnType=ColumnType.INTEGER)
	private int age;

 
	private Teacher teacher;

	private List<Teacher> teachers;


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

	@OneToOne
	public Teacher getTeacher() {
		return teacher;
	}

 
	@OneToOne
	public Teacher holderTeacher() {
		return teacher;
	}


 
	@OneToMany
	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "age="+age+"  stuname="+stuName+" id="+id;
	}
}
```
 
 *一对一的写法需要，在两个表中的其中一个设置外键关联，然后在另外的一个类中取值的时候
 *可以直接在 get方法上用 @OneToOne 标注，就可以直接拿取到关联对象
  
  
  
  
####一对多：
*一对多和一对一在表的设计中大体相同，但是表的关联外键必须放在多的一方，
并且在少的一方取对象的时候 要用@OneToMany
#####如下：
```java


@Table(name="student")
public class Student {
    
    @TableID(name="sid",isPrimaryKey=true,defaultVal=0,type= Parmary.AutoIncrement,columnType= ColumnType.INTEGER)
	private int id ;
	
	@TableColumn(name="stuname",columnType=ColumnType.TEXT)
	private String stuName;
	
	
	@TableColumn(name="age",columnType=ColumnType.INTEGER)
	private int age;

 
	private Teacher teacher;

	private List<Teacher> teachers;


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

	@OneToOne
	public Teacher getTeacher() {
		return teacher;
	}

 

 
	@OneToMany
	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "age="+age+"  stuname="+stuName+" id="+id;
	}
}



```


####多对一
多对一和一对多一样只是将谁设置成多或者一 是根据业务上来定的，技术上无差别










####多对多

MIniOrm2在设计多对多的表关系和大多的orm框架一样 都采用了第三个表，可以大大降低开发
的难度和维护的成本

下面来介绍一下多对多的表设置

```java


@Table(name="student")
public class Student {
    
    @TableID(name="sid",isPrimaryKey=true,defaultVal=0,type= Parmary.AutoIncrement,columnType= ColumnType.INTEGER)
    private int id ;
	
	@TableColumn(name="stuname",columnType=ColumnType.TEXT)
	private String stuName;
	
	
	@TableColumn(name="age",columnType=ColumnType.INTEGER)
	private int age;

 
 
	private List<Teacher> teachers;


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

 

 

 
    @ManyToMany(bridgingTable=StudentTeacher.class)
	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@Override
	public String toString() {
		return "age="+age+"  stuname="+stuName+" id="+id;
	}
}







```

```java

     @Table(name="userTable")
    public class Teacher {

    @TableColumn(name="username",columnType= ColumnType.TEXT)
    private  String userName;

	@TableColumn(name="pwd",columnType=ColumnType.TEXT)
	private  String pwd;
	

	@TableID(isPrimaryKey=true,name="userid",defaultVal=0,type= Parmary.CUSTOM,columnType=ColumnType.INTEGER)
	private long id;
	
 
	@TableColumn(name="sex",columnType=ColumnType.VARCHAR)
	private  String sex;
	@TableColumn(name="shengao",columnType=ColumnType.INTEGER)
	private  int shengao;

	@TableColumn(name="isGril",columnType= ColumnType.BOOLEAN,IgnoreBooleanParam = true)
	private  boolean isGril;


	private ArrayList<Student> students;




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
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

 

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getShengao() {
		return shengao;
	}

	public void setShengao(int shengao) {
		this.shengao = shengao;
	}

	public boolean isGril() {
		return isGril;
	}

	public void setIsGril(boolean isGril) {
		this.isGril = isGril;
	}
    
    @ManyToMany(bridgingTable=StudentTeacher.class)

    public  ArrayList<Student> getStudents(){
    return students;
    
    }
    
}
 
```
**中间表
```java

@Table(name="StudentTeacher")
public class StudentTeacher {

    @TableID(isPrimaryKey=true,name="utid",defaultVal=0,type= Parmary.AutoIncrement,columnType= ColumnType.INTEGER)
    private long id;

    @TableColumn(name="sid",isForeignkey=true,columnType=ColumnType.INTEGER,isPrimaryKey = true)
    private Student student;


    @TableColumn(name="tid",isForeignkey=true,columnType=ColumnType.INTEGER,isPrimaryKey = true)
    private Teacher teacher;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}

```
在设置多对多的关系的同时，要指定中间表的class类。


****注意：千万不要使用JSON工具直接打印对象 否则可能会出现死循环


 
 
