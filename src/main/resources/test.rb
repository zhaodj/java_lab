require "java"

java_import "java.util.ArrayList"
java_import "com.zhaodj.foo.script.User"
java_import "com.zhaodj.foo.script.Room"
java_import "com.zhaodj.foo.script.EventResult"

def add(max)
	i = 0
	sum = 0
	while i < max
		sum = sum + i
		i = i + 1
	end
#	puts sum
end

def setParents(user,room)
	arr = ArrayList.new
	e1 = EventResult.new
	e1.userName = user.name
	e1.num = 5
	e1.type = 1
	e1.roomId = room.roomId
	e2 = EventResult.new
	e2.userName = user.name
	e2.num = 2
	e2.type = 2
	e2.roomId = room.roomId
	arr.add e1
	arr.add e2
	arr
end