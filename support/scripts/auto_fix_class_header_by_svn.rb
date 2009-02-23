#	Copyright 2008 The Ganshane Network Service Team.
#	site:http://ganshane.net
#	file : $Id: auto_fix_class_header_by_svn.rb 718 2008-09-23 02:54:49Z jcai $
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

dir = File.dirname(__FILE__)+'/../../src'
#file='/home/jcai/workspace/piano-core/src/main/java/com/bjmaxinfo/piano/model/common/supchain/link/MoMaterialStockLink.java'
Find.find(dir)  do |file|
  if file=~/\.java$/
    puts file
    
    tmp_file_name=file+'.tmp'
    ctx.log(file,1,"HEAD",1,true,nil){|changed_paths, rev, author, date, message|
      tmpFile=File.open(tmp_file_name,'w')
      begin_read=true
      has_write_head=false
      File.open(file).each{|line|
        next if(begin_read&&!(line=~/package/))
        begin_read=false
        if(!has_write_head)
         currentYear = Time.now.year
        createdYear = date.year
        
        copyYears=createdYear.to_s+"-"+currentYear.to_s
        copyYears=currentYear.to_s if currentYear == createdYear
        
        puts copyYears
        
         tmpFile<<"/* 
 * Copyright #{copyYears} The Lichen Team.
 * 
 * Licensed under the Apache License, Version 2.0 (the \"License\");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an \"AS IS\" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */\n"
         has_write_head=true
         end
         tmpFile<<line
      }
      tmpFile.close
      File.rename(tmp_file_name,file)
    }
  end
end
