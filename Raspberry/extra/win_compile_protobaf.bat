@echo off
set PROTOC=C:\Program Files\protoc-2.5.0-win32\protoc.exe
set OUT_PATH=D:\Develop\MbLock\Raspberry\mblock\proto
set PROTO_FILES=D:\Develop\MbLock\proto\mblock
for %%F IN ("%PROTO_FILES%\*.proto") do (
    echo Fount %%~nxF
    "%PROTOC%" --python_out="%OUT_PATH%" "%%F" --proto_path="%PROTO_FILES%"
)