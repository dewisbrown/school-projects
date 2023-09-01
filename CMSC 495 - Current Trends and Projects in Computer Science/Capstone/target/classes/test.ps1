[CmdletBinding()]
	param(
		[Parameter(Mandatory, Position=0)]
		[ValidateNotNullOrEmpty()]
		[string]$InputFile,
		[Parameter(Mandatory, Position=1)]
		[ValidateNotNullOrEmpty()]
		[string]$OutputDirectory
	)
	$Script:Banner = @"
====================================================================================================================================
| A Super Cool Project Name: Windows Event Log Parser    | [Version] 0.5.0-beta                                                    |
====================================================================================================================================
| [Authors] bike, CLUMGC2023, Shaybabe, scuba_steve      | [Some Other Info]                                                       |
====================================================================================================================================
"@

$Script:Banner2 = @"

    ___       _____                          ______            __   ____               _           __     _   __                   
   /   |     / ___/__  ______  ___  _____   / ____/___  ____  / /  / __ \_________    (_)__  _____/ /_   / | / /___ _____ ___  ___ 
  / /| |     \__ \/ / / / __ \/ _ \/ ___/  / /   / __ \/ __ \/ /  / /_/ / ___/ __ \  / / _ \/ ___/ __/  /  |/ / __ ``/ __ ``__ \/ _ \
 / ___ |    ___/ / /_/ / /_/ /  __/ /     / /___/ /_/ / /_/ / /  / ____/ /  / /_/ / / /  __/ /__/ /_   / /|  / /_/ / / / / / /  __/
/_/  |_|   /____/\__,_/ .___/\___/_/      \____/\____/\____/_/  /_/   /_/   \____/_/ /\___/\___/\__/  /_/ |_/\__,_/_/ /_/ /_/\___/ 
                     /_/                                                        /___/                                               
					 
"@

function Get-LogonLogOffEvents {

	[CmdletBinding()]
	param(
		[Parameter(Mandatory, Position=0)]
		[ValidateNotNullOrEmpty()]
		[string]$InputFile
	)

	BEGIN {

		$XMLFilter = "<QueryList><Query Id='0' Path='file://$($InputFile)'><Select>*[System[(EventID=4624) or (EventID=4647) or (EventID=4634)]]</Select></Query></QueryList>"

		$MSObjsCodes = @{
			'%%1842' = "Yes"
			'%%1843' = "No"
		}

		$EventIDs = @{
			4624 = "4624"
			4647 = "4647"
			4634 = "4634"
		}
	}

	PROCESS {

		$Events = Get-WinEvent -FilterXml $XMLFilter -ErrorAction SilentlyContinue

		foreach ($Event in $Events) {
			$XMLEvent = [xml]$Event.ToXml()
			$UserName = $XMLEvent.Event.EventData.Data.Where({$_.name -eq 'TargetUserName'}).'#text'
			$MSObjsCode = $XMLEvent.Event.EventData.Data.Where({$_.name -eq 'ElevatedToken'}).'#text'
			$EventID = $Event.Id

			$Properties = [ordered]@{
				TimeCreated = $Event.TimeCreated
				UserName = $UserName
				Event = ($EventIDs.GetEnumerator() | Where-Object {$_.Key -eq $EventID}).Value
				LogonType = $XMLEvent.Event.EventData.Data.Where({$_.name -eq 'LogonType'}).'#text'
				IPAddress = $XMLEvent.Event.EventData.Data.Where({$_.name -eq 'IpAddress'}).'#text'
				LogonID = $XMLEvent.Event.EventData.Data.Where({$_.name -eq 'TargetLogonID'}).'#text'
				LinkedLogonID = $XMLEvent.Event.EventData.Data.Where({$_.name -eq 'TargetLinkedLogonID'}).'#text'
				ElevatedToken = ($MSObjsCodes.GetEnumerator() | Where-Object {$_.Key -eq $MSObjsCode}).Value
			}

			$LoginEventObj = New-Object -Type psobject -Property $Properties
			Write-Output $LoginEventObj
		}
	}

	END {}
}

function Get-FailedLogonEvents {

	[CmdletBinding()]
	param(
		[Parameter(Mandatory, Position=0)]
		[ValidateNotNullOrEmpty()]
		[string]$InputFile
	)

	BEGIN {

		$XMLFilter = "<QueryList><Query Id='0' Path='file://$($InputFile)'><Select>*[System[(EventID=4625)]]</Select></Query></QueryList>"

                $MSObjsCodes = @{
					'%%1842' = "Yes"
					'%%1843' = "No"
		}

                $EventIDs = @{
                    4625 = "4625"
                }
        }

	PROCESS {

		$Events = Get-WinEvent -FilterXml $XMLFilter -ErrorAction SilentlyContinue

		foreach ($Event in $Events) {
			$XMLEvent = [xml]$Event.ToXml()
			$UserName = $XMLEvent.Event.EventData.Data.Where({$_.name -eq 'TargetUserName'}).'#text'
			$FailureReason = $XMLEvent.Event.EventData.Data.Where({$_.name -eq 'Status'}).'#text'
            $MSObjsCode = $XMLEvent.Event.EventData.Data.Where({$_.name -eq 'ElevatedToken'}).'#text'
			$EventID = $Event.Id

			$Properties = [ordered]@{
				TimeCreated = $Event.TimeCreated
				UserName = $UserName
                Event = ($EventIDs.GetEnumerator() | Where-Object {$_.Key -eq $EventID}).Value				
                LogonType = $XMLEvent.Event.EventData.Data.Where({$_.name -eq 'LogonType'}).'#text'
				IPAddress = $XMLEvent.Event.EventData.Data.Where({$_.name -eq 'IpAddress'}).'#text'
				FailureReason = $FailureReason
				LogonProcess = $XMLEvent.Event.EventData.Data.Where({$_.name -eq 'LogonProcessName'}).'#text'
                ElevatedToken = ($MSObjsCodes.GetEnumerator() | Where-Object {$_.Key -eq $MSObjsCode}).Value
                        }

			$FailedLogonEventObj = New-Object -Type psobject -Property $Properties
			Write-Output $FailedLogonEventObj
		}
	}

	END {}
}

function Get-ScheduledTaskEvents {

	[CmdletBinding()]
	param(
		[Parameter(Mandatory, Position=0)]
		[ValidateNotNullOrEmpty()]
		[string]$InputFile
	)

	BEGIN {

		$XMLFilter = "<QueryList><Query Id='0' Path='file://$($InputFile)'><Select>*[System[(EventID=4698)]]</Select></Query></QueryList>"

			$MSObjsCodes = @{
					'%%1842' = "Yes"
					'%%1843' = "No"
			}

            $EventIDs = @{
                    4698 = "4698"
            }

 	}
        
	PROCESS {

		$Events = Get-WinEvent -FilterXml $XMLFilter -ErrorAction SilentlyContinue

		foreach ($Event in $Events) {
			$XMLEvent = [xml]$Event.ToXml()
			$XMLContent = [xml]$XMLEvent.Event.EventData.Data.Where({$_.name -eq 'TaskContent'}).'#text'
            $EventID = $Event.Id
			
			foreach ($Task in $XMLContent.Task.Actions.Exec) {
				$Properties = [ordered]@{
					TimeCreated = $Event.TimeCreated
					Name = $Event.Name
					Event = ($EventIDs.GetEnumerator() | Where-Object {$_.Key -eq $EventID}).Value
					Command = $Task.Command
					Arguments = $Task.Arguments
				}
	
				$ScheduledTaskEventObj = New-Object -Type psobject -Property $Properties
				Write-Output $ScheduledTaskEventObj
			}			
		}
	}

	END {}
}

function Show-ConsoleResults {
	param (
		[Parameter(Mandatory, Position=0)]
		[ValidateNotNullOrEmpty()]
		[int]$EventCount,
		[Parameter(Mandatory, Position=1)]
		[ValidateNotNullOrEmpty()]
		[string]$EventType
	)
	
	$EventStr = "events"

	if ($EventCount -eq 1) {
		$EventStr = "event"
	}

	Write-Host "   [$($EventCount.ToString())] " -ForegroundColor Green -NoNewline
	Write-Host "$($EventType) $($EventStr)"
}

$Script:Banner
$Script:Banner2

if ([System.IO.Path]::GetExtension($InputFile) -eq ".evtx") {
	if ([System.IO.File]::Exists($InputFile)) {

		if (![System.IO.Directory]::Exists($OutputDirectory)) {
			Write-Warning "$OutputDirectory does not exist"
			Write-Warning "Creating path $OutputDirectory"
			New-Item -Path $OutputDirectory -ItemType Directory | Out-Null
		}

		$UnsupportedLogTypes = [System.Collections.ArrayList]@()
		$EmptyLogs = [System.Collections.ArrayList]@()

		try {
			$Filename = (Get-ItemProperty -Path $InputFile).Name
			$EventLogType = (Get-WinEvent -Path $InputFile -MaxEvents 1 -ErrorAction Stop).LogName

			switch($EventLogType) {
				"Security" {

					Write-Host "`n[+] Parsing $FileName"

					# Parse logon/log off events
					$LogonLogOffEvents = Get-LogonLogOffEvents -InputFile $InputFile
					# Get number of logon/log off events
					$LogonLogOffEventCount = ($LogonLogOffEvents | Measure-Object).Count

					# Parse failed logon events
					$FailedLogonEvents = Get-FailedLogonEvents -InputFile $InputFile
					# Get number of failed logon events
					$FailedLogonEventCount = ($FailedLogonEvents | Measure-Object).Count

					#Parse scheduled task events
					$ScheduledTaskEvents = Get-ScheduledTaskEvents -InputFile $InputFile
					# Get number of scheduled task events
					$ScheduledTaskEventCount = ($ScheduledTaskEvents | Measure-Object).Count

					# Display event counts to console
					Show-ConsoleResults -EventCount $LogonLogOffEventCount -EventType "Logon/Log Off"
					Show-ConsoleResults -EventCount $FailedLogonEventCount -EventType "Failed Logon"
					Show-ConsoleResults -EventCount $ScheduledTaskEventCount -EventType "Scheduled Task Created"

					Write-Host "`n"

					# Do the following if the event log contains logon/log off events
					if ($LogonLogOffEvents) {
						# Create a string containing the file path of the output file
						$LogonLogOffOutputFile = "$OutputDirectory\$((Get-Date).ToString('MM-dd-yyy'))_LogonLogOffEvents.csv"

						# Export events to csv file
						$LogonLogOffEvents | Export-Csv -Path $LogonLogOffOutputFile -NoTypeInformation

						Write-Host "[+] Created CSV: " -NoNewline
						Write-Host "$LogonLogOffOutputFile" -ForegroundColor Yellow
					}

					# Do the following if the event log contains failed logon events
					if ($FailedLogonEvents) {
						# Create a string containing the file path of the output file
						$FailedLogonOutputFile = "$OutputDirectory\$((Get-Date).ToString('MM-dd-yyy'))_FailedLogonEvents.csv"
						
						# Export events to csv file
						$FailedLogonEvents | Export-Csv -Path $FailedLogonOutputFile -NoTypeInformation

						Write-Host "[+] Created CSV: " -NoNewline
						Write-Host "$FailedLogonOutputFile" -ForegroundColor Yellow
					}

					# Do the following if the event log contains scheduled task events
					if ($ScheduledTaskEvents) {
						# Create a string containing the file path of the output file
						$ScheduledTaskOutputFile = "$OutputDirectory\$((Get-Date).ToString('MM-dd-yyy'))_ScheduledTaskEvents.csv"

						# Export events to csv file
						$ScheduledTaskEvents | Export-Csv -Path $ScheduledTaskOutputFile -NoTypeInformation

						Write-Host "[+] Created CSV: " -NoNewline
						Write-Host "$ScheduledTaskOutputFile" -ForegroundColor Yellow
					}

				}
				default {
					$null = $UnsupportedLogTypes.Add($Filename)
				}
			}
		}
		catch [System.Exception]{
			if ($_.Exception -match "No events were found that match the specified selection criteria") {
				$null = $EmptyLogs.Add($Filename)
			}
		}

		if ($UnsupportedLogTypes) {
			foreach ($Log in $UnsupportedLogTypes) {
				Write-Host "[-] Failed to parse $Log " -NoNewline
				Write-Host "[Unsupported log type]" -ForegroundColor Yellow
			}
		}
		if ($EmptyLogs) {
			foreach ($Log in $EmptyLogs) {
				Write-Host "[-] Failed to parse $Log " -NoNewline
				Write-Host "[Empty log file]" -ForegroundColor Red
			}
		}
	}
	else {
		Write-Warning -Message "$InputFile does not exist.`n"
	}
}
else {
	Write-Warning -Message "Wrong file type. This application only parses evtx files.`n"
}
