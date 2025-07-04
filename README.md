[![Codacy Badge](https://app.codacy.com/project/badge/Grade/4807b26c2e91478b98af50bcf1ea5814)](https://app.codacy.com/gh/winterhavenmc/SavageDeathBan/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)[![Spigot Version](https://badgen.net/static/spigot-api/1.21.7?color=yellow)](https://spigotmc.org)
&nbsp;[![License](https://badgen.net/static/license/GPLv3)](https://www.gnu.org/licenses/gpl-3.0)

### Description
This plugin temp bans a player on death for a configurable period of time.

### Features
*  Ban a player and optionally ban their IP address for a configurable amount of time
*  Integrates with the Bukkit ban system, defers to built in commands to view ban list or unban player/ip
*  Per world enabled in configuration
*  Customizable messages and language localization
*  Optional sound effects

### Commands
| Command                              | Description                                          |
|--------------------------------------|------------------------------------------------------|
| /deathban&nbsp;reload                | Reloads configuration file and messages              |
| /deathban&nbsp;status                | Displays version info and some config settings       |
| /deathban&nbsp;help&nbsp;\[command\] | Displays a brief help message and command usage      |
| /banlist                             | Server provided command to list current bans         |
| /unban \[player name\]               | Server provided command to unban a player by name    |
| /unbanip \[player ip address\]       | Server provided command to unban a player IP address |

### Permissions
| Permission      | Description                                                | Default |
|-----------------|------------------------------------------------------------|---------|
| deathban.admin  | Administrator permission, grants all following permissions | op      |
| deathban.reload | Allows use of plugin reload command                        | op      |
| deathban.status | Allows a user plugin configuration settings                | op      |
| deathban.exempt | Makes a user exempt from death bans                        | op      |

### Configuration
All configuration changes can be made without needing to restart your server. Just issue the reload command when 
you are satisfied with your settings in config.yml.
