package me.scratchone.util;

import java.util.UUID;

public final class UuidUtil {

	private UuidUtil() {}

	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-","");
	}

	public static String getUuidNoReplacement(){
		return UUID.randomUUID().toString();
	}
}
