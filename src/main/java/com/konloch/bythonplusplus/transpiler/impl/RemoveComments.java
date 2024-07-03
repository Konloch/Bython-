package com.konloch.bythonplusplus.transpiler.impl;

import com.konloch.bythonplusplus.BythonPlusPlus;
import com.konloch.bythonplusplus.transpiler.TranspileStage;

/**
 * @author Konloch
 * @since 7/2/2024
 */
public class RemoveComments implements TranspileStage
{
	@Override
	public String fromPython(BythonPlusPlus bpp, String code)
	{
		return removeComments(code);
	}
	
	@Override
	public String fromBythonPP(BythonPlusPlus bpp, String code)
	{
		return removeComments(code);
	}
	
	/**
	 * Removes all comments that start with // it will throw the code away entirely
	 *
	 * @param code any code
	 * @return the formatted string
	 */
	public String removeComments(String code)
	{
		StringBuilder transpiledCode = new StringBuilder();
		boolean inString = false;
		boolean escapeFlag = false;
		int commentSlashCount = 0;
		StringBuilder buffer = new StringBuilder();
		
		for (char c : code.toCharArray())
		{
			if (c == '"' && !escapeFlag)
			{
				buffer.append(c);
				inString =! inString;
			}
			else if (c == '\\' &&inString)
			{
				buffer.append(c);
				escapeFlag =! escapeFlag;
			}
			else if (c == '/' &&!inString)
			{
				commentSlashCount++;
				if (commentSlashCount == 2)
				{
					if(buffer.length() > 0)
						transpiledCode.append(buffer);
					
					buffer.setLength(0);
				}
			}
			else if (c == '\n' || c == '\r')
			{
				if(inString)
					inString = false;
				if(escapeFlag)
					escapeFlag = false;
				
				if (commentSlashCount > 0)
					commentSlashCount = 0;
				else
				{
					transpiledCode.append(buffer).append("\n");
					buffer.setLength(0);
				}
			}
			else if (commentSlashCount == 0)
			{
				if(escapeFlag)
					escapeFlag = false;
				
				buffer.append(c);
			}
		}
		
		if (buffer.length() > 0)
			transpiledCode.append(buffer);
		
		return transpiledCode.toString();
	}
}
