package com.mini_orm.android.parseType;

import java.util.HashMap;

public class ParseTypeFactory {

	public static HashMap<String, ParseTypeInterface>  parserMap=new HashMap<String, ParseTypeInterface>();
	
	static{
		StringParser stringParser=new StringParser();
		parserMap.put(String.class.getName(),stringParser );
		
		IntegerParser  integerParser=new IntegerParser();
		parserMap.put(IntegerParser.class.getName(), integerParser );
		parserMap.put(int.class.getName(), integerParser );

		BooleanParser booleanParser=new BooleanParser();
		parserMap.put(boolean.class.getName(), booleanParser );
		parserMap.put(Boolean.class.getName(), booleanParser );

		DoubleParser doubleParser=new DoubleParser();
		parserMap.put(Double.class.getName(), doubleParser );
		parserMap.put(double.class.getName(), doubleParser );

		FloatParser  floatParser=new FloatParser();
		
		parserMap.put(Float.class.getName(), floatParser );
		parserMap.put(float.class.getName(), floatParser );

	}
	
	
	
	public ParseTypeInterface   getFieldParser(Class type){


		return parserMap.get(type.getName());
	}
	
	
	
	
	
}
