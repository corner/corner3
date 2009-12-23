#	Copyright 2008 The Ganshane Network Service Team.
#	site:http://ganshane.net
#	file : $Id$
#	created at:2008-05-21


$KCODE = 'u'
require 'find'
require 'svn/client.rb'

ctx=Svn::Client::Context.new
ctx.add_simple_prompt_provider(0) do |cred, realm, may_save, pool|
      cred.username = ENV['SVN_USER']
      cred.password = ENV['SVN_PASSWORD']
      cred.may_save = false
end
dir = File.dirname(__FILE__)+'/../../'
#file='/home/jcai/workspace/piano-core/src/main/java/com/bjmaxinfo/piano/model/common/supchain/link/MoMaterialStockLink.java'

Find.find(dir)  do |file|
  next if ( !(file =~ /src/  ||  file =~ /webapp/) )
  next if file =~ /\.hbm\.xml/

  if file=~/\.xml$/ || file=~/\.tml$/ || file=~/\.library$/ || file=~/\.page$/ || file=~/\.script$/ 
      puts file
       tmp_file_name=file+'.tmp'
       tmp_file=File.open(tmp_file_name,'w')
       is_content=false
       File.open(file).each{|line|
        is_content =true if (line =~ /<[\w|!]/  && !(line =~ /<!--/))
        tmp_file << line if is_content
      }
       tmp_file.close
      File.rename(tmp_file_name,file)
    end
end

Find.find(dir)  do |file|
  next if ( !(file =~ /src/  ||  file =~ /webapp/ ) )
  next if file =~ /\.hbm\.xml/

  if file=~/\.xml$/ || file=~/\.tml$/ || file=~/\.library$/ || file=~/\.page$/ || file=~/\.script$/ 
      puts file
       tmp_file_name=file+'.tmp'
       tmp_file=File.open(tmp_file_name,'w')

      ctx.log(file,1,"HEAD",1,true,nil){|changed_paths, rev, author, date, message|
      	      	
      currentYear = Time.now.year
      createdYear = date.year
      
     
      copyYears=createdYear.to_s+"-"+currentYear.to_s
      copyYears=currentYear.to_s if currentYear == createdYear
      
      puts copyYears

          tmp_file << "<?xml version=\"1.0\" encoding=\"utf-8\"?>
<!-- 
	Copyright #{copyYears} The Ganshane Network Service Team. 
	site: http://ganshane.net
	file : $Id$
	created at:#{date.strftime("%Y-%m-%d")}
-->
"
         
       }
       File.open(file).each{|line|
          tmp_file << line
        }
       tmp_file.close
      File.rename(tmp_file_name,file)
    end
end