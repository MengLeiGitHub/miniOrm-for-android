package com.miniorm.android.parseType;

import android.util.Log;

import com.miniorm.android.parseType.parser.BooleanParser;
import com.miniorm.android.parseType.parser.DoubleParser;
import com.miniorm.android.parseType.parser.FloatParser;
import com.miniorm.android.parseType.parser.IntegerParser;
import com.miniorm.android.parseType.parser.StringParser;
import com.miniorm.dao.BaseDao;

import java.util.HashMap;

public class ParseTypeFactory {

	public static HashMap<String, ParseTypeInterface>  parserMap=new HashMap<String, ParseTypeInterface>();

	public static HashMap<String,BaseDao>   entityParse=new HashMap<String,BaseDao>();


	static{
		StringParser stringParser=new StringParser();
		parserMap.put(String.class.getName(),stringParser );
		
		IntegerParser integerParser=new IntegerParser();
		parserMap.put(IntegerParser.class.getName(), integerParser );
		parserMap.put(int.class.getName(), integerParser );

		BooleanParser booleanParser=new BooleanParser();
		parserMap.put(boolean.class.getName(), booleanParser );
		parserMap.put(Boolean.class.getName(), booleanParser );

		DoubleParser doubleParser=new DoubleParser();
		parserMap.put(Double.class.getName(), doubleParser );
		parserMap.put(double.class.getName(), doubleParser );

		FloatParser floatParser=new FloatParser();
		
		parserMap.put(Float.class.getName(), floatParser );
		parserMap.put(float.class.getName(), floatParser );

	}
	
	
	
	public  static ParseTypeInterface   getFieldParser(Class type){


		return parserMap.get(type.getName());
	}
	public  static ParseTypeInterface   getFieldParser(String classname){


		return parserMap.get(classname);
	}

	public  static  void  addEntityParse(String name,BaseDao dao){
		Log.e("tag","addEntityParse      "+name);
		  entityParse.put(name,dao);
	}

	public static  BaseDao   getEntityParse(String name){
		Log.e("tag","getEntityParse   "+name);

		return entityParse.get(name);
	}
	
	
}
