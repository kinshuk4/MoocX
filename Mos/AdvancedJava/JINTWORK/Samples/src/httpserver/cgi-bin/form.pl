#!C:\Perl\bin\Perl.exe

use CGI qw(:standard);
use CGI qw(param);

# query request parameters
my $name = param("NAME");
my $email = param("EMAIL");

# print document header
print header();  

# print confirmation
print "<html><body>Thanks for your name <b>$name</b> and address <b>$email</b><body></html>\n";
