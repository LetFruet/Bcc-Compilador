.assembly extern mscorlib {} 
.assembly _codigo_objeto{} 
.module _codigo_objeto.exe 
.class public UNICA{ 
.method static public void _principal() { 
.entrypoint 
.locals (int64 i_lado) 
.locals (int64 i_area) 
call string [mscorlib]System.Console::ReadLine() 
call int64 [mscorlib]System.Int64::Parse(string) 
stloc i_lado
ldloc i_lado 
conv.r8 
ldloc i_lado 
conv.r8 
mul 
conv.i8 
stloc i_area 
ldloc i_area 
conv.r8 
conv.i8 
call void [mscorlib]System.Console::Write(int64) 
ret 
 } 
}