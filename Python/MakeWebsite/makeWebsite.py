# makeWebsite.py
# authors: Kexin Huang and Weixi Ma

def safeOpen(fileName):
    try:
        fo = open(fileName)
    except IOError, e: # if operation fails with IOError, then execute:
        print 'said file does not exist'
        return True
    else:
        fileList = []
        listFile = []
        fileList = fo.readlines()
        for line in fileList:
            listFile.append(line.strip())
            fo.close()
        print('file had been opened')
        return listFile
    
def detect_name(listFile):
    '''Detects the name of student'''
    firstLine = listFile[0]
    capital = firstLine[0]
    if capital not in 'ABCDEFGHIJKLMNOPQRSTUVWXYZ':
        raise ValueError, 'The first line has to be just the name with proper capitalizaiton'
    return firstLine #string

def detect_emails(listFile):
    '''Detects emails part in resume'''
    email = []
    for line in listFile:
        if line.find('@') >= 0:
            if line[-4:].find('.edu') >= 0 or line[-4:].find('.com') >= 0:
                check = line.split('@')[1]
                if check.lower() == check:
                    email.append(line)
    strEmail = ','.join(email)
    return strEmail # return a string
    
def detect_courses(listFile):
    '''Find all courses in resume'''
    for line in listFile:
        if line.find('Courses') >= 0:
            courses = line.split('Courses', 1)[1].strip()
            for i in range(1,len(courses)):
                if courses[i].isalpha() == True:
                    endpoint = courses[i]
                    break
            output_courses = endpoint + courses.split(endpoint,1)[1]
    return output_courses #string

def detect_projects(listFile):
    '''Finds all projects in resume'''
    dic = {}
    projects = []
    for i in range (len(listFile)):  #convert listFile into a dic dic[lineNum]=lineContent
        dic[i] = listFile[i]
    for key in dic.keys():
        if dic[key].find('Projects') >= 0:
            startCon = key
        if dic[key].find('----------') >= 0:
            enCon = key
    for x in range (startCon + 1, enCon - 1):#add all info between 'Projects' and '------'
        projects.append(dic[x])
    return projects  #list

def detect_education(listFile):
    '''Find all education background in resume'''
    education = []
    for line in listFile:
        if line.find('University') >= 0 or line.find('university') >= 0:
            if line.find('Master') >= 0 or line.find('Bachelor') >= 0 or line.find('Doctor') >= 0:
                education.append(line)
    return education # list
            
# HTML input part
def surround_block(tag, text):
    '''surround some text in an html block'''
    taglst = list(tag)
    taglst.insert(1,'/')
    tagstr = ''.join(taglst)
    #return something like '<h1>|apple|</h1>'
    return tag + '|' + text + '|' + tagstr

def write_intro(name, email, html):
    '''make the intro section of the resume into html format and write it into a file'''
    str1 = surround_block('<h1>', name)
    str2 = surround_block('<p>', email)
    str3 = surround_block('<div>', str1+'|'+str2)
    #split the string according to '|' and make it a list 
    lines = str3.split('|')
    #write every single element a line in the file
    for i in range(0, len(lines)):
        html.write(lines[i]+'\n')

def write_degrees(degrees, html):
    '''make the education section into html format and write it into a file'''
    str1 = surround_block('<h2>', 'Education')
    str2 = ''
    for i in range(0,len(degrees)):
        str2 = str2 + '|' + surround_block('<li>', degrees[i])
    #have the '|' at the beginning which have to be removed
    str2lst = str2.split('|')
    #we have an empty string the first index
    str2lst.remove('')
    str2 = '|'.join(str2lst)
    str3 = str1 + '|' + surround_block('<ul>', str2)
    str4 = surround_block('<div>', str3)
    #split the string according to '|' and make it a list
    lines = str4.split('|')
    #write every single element a line in the file
    for i in range(0, len(lines)):
        html.write(lines[i]+'\n')

def write_projects(projects, html):
    '''make the projects section into html format and write it into a file'''
    str1 = surround_block('<h2>', 'Projects')
    str2 = ''
    for i in range(0,len(projects)):
        str2 = str2 + '|' + surround_block('<li>', surround_block('<p>', projects[i]))
    #have the '|' at the beginning which have to be removed
    str2lst = str2.split('|')
    #we have an empty string the first index
    str2lst.remove('')
    str2 = '|'.join(str2lst)
    str3 = str1 + '|' + surround_block('<ul>', str2)
    str4 = surround_block('<div>', str3)
    #split the string according to '|' and make it a list
    lines = str4.split('|')
    #write every single element a line in the file
    for i in range(0, len(lines)):
        html.write(lines[i]+'\n')

def write_courses(courses, html):
    '''make the course section into html format and write it into a file'''
    str1 = surround_block('<h3>', 'Courses')
    str2 = surround_block('<span>', courses)
    str3 = surround_block('<div>', str1 + '|' + str2)
    #split the string according to '|' and make it a list
    lines = str3.split('|')
    #write every single element a line in the file
    for i in range(0, len(lines)):
        html.write(lines[i]+'\n')
    
def write_begin(html):
    '''write the first line'''
    html.write('<div id="page-wrap">\n')

def write_end(html):
    '''write the last 3 lines in the file'''
    #write every single tag a line in the file
    html.write('</div>\n')
    html.write('</body>\n')
    html.write('</html>')


def evaluateOnce():
    '''main part to convert a txt file into a html'''

    #read txt part, keep asking user if the file doesn't exist
    while True:
        txtName = raw_input("What's the file's name that you want to convert to web page? (eg:resume.txt)")
        listFile = safeOpen(txtName)
        if listFile != True:
            break
    name = detect_name(listFile)
    email = detect_emails(listFile)
    courses = detect_courses(listFile)
    projects = detect_projects(listFile)
    education = detect_education(listFile)

    #input html part
    htmlName = txtName.rstrip('txt') + 'html'
    f = open('resume_initial.html', 'r')
    lines = f.readlines() #read model lines in html.initial
    f.close
    
    fo = open(htmlName,'a+') #create a new html file to keep initial html safe
    fo.seek(0)
    fo.truncate()
    del lines[-1]
    del lines[-1]
    fo.writelines(lines)
    write_begin(fo)    

    #input html part
    write_intro(name, email, fo)
    write_degrees(education, fo)
    write_projects(projects, fo)
    write_courses(courses, fo)
    write_end(fo)
    fo.close

    print 'The file with name', htmlName, 'has been created'
    
def main():
    evaluateOnce()
    continueChoice = raw_input('do you want to continue? Press n and enter to quit')
    while continueChoice !='n':
        evaluateOnce()
        continueChoice = raw_input('do you want to continue? Press n and enter to quit')

        


if __name__ == '__main__':
    main()



##            
####    
##listFile = safeOpen('resumetest.txt') #write txt into a file:
##print listFile
######print detectName(listFile)
####print detect_emails(listFile) # print email
####
##print detect_courses(listFile)
##print detect_projects(listFile)

##print detect_education(listFile)
##
####print '\r\n'
####my_string="hello python world , i'm a beginner "
####print my_string.split("world",1)[1]
