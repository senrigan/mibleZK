@echo off

rem
rem Copyright (C) 2001 Christopher Brind
rem


echo.
echo Usage:
echo    example
echo.
java -cp classes; example.Main

if ERRORLEVEL 1 goto :Error
goto :End


:Error
echo.
echo.
echo There was an error while trying to run the example, please make sure
echo  it has been built using:
echo  ant build example
echo.
echo Then re-run this file.
goto :End


:End