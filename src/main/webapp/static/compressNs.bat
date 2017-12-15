@echo off

echo Do you want to compress js(css) for atminspect
pause
cd %~dp0

echo compress js for PC site

call compressor\compressor.bat ns\addmac
call compressor\compressor.bat ns\data
call compressor\compressor.bat ns\inspection
call compressor\compressor.bat ns\removemac

echo compress js for mobile

call compressor\compressor.bat nsmobile\pages\js

echo.
echo 压缩完成
pause
echo on