# PowerShell

## Enable running PowerShell scripts:

```powershell
Set-ExecutionPolicy RemoteSigned
```
## Profiles

To display the path to the profile

```powershell
$profile
```

To test if the the profile exists

```powershell
Test-Path $profile
```

To create a profile

```powershell
New-Item -Path $profile -ItemType file -Force
```

To open the profile

```powershell
notepad $profile
```

## Ignore SSL certificate

```powershell
[System.Net.ServicePointManager]::ServerCertificateValidationCallback = {$true}
```
