# 
# Copyright 2008 The Corner Team.
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

$KCODE = 'u'
require "find"


# 对java文件以及html，xml文件加上 subversion的属性
# 
# @author: Jun Tsai
# @version $Revision$
# @since 0.0.1
# 

dir = File.dirname(__FILE__)+'/../../src'

Find.find(dir)  do |file|
      extname=File.extname(file)
      if(extname.eql?(".js")||extname.eql?(".page")||extname.eql?(".tml")||extname.eql?(".java")||extname.eql?(".html")||extname.eql?(".xml"))
        system("svn pd svn:keywords #{file}")
        system("svn ps svn:keywords \"Id Revision Date Author\" #{file}")
      end
end

dir = File.dirname(__FILE__)+'/../../support'

Find.find(dir)  do |file|
      extname=File.extname(file)
      if(extname.eql?(".rb")||extname.eql?(".tml")||extname.eql?(".java")||extname.eql?(".html")||extname.eql?(".xml"))
        system("svn pd svn:keywords #{file}")
        system("svn ps svn:keywords \"Id Revision Date Author\" #{file}")
      end
end
            
