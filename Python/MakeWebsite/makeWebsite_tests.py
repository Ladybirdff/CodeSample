#cit 590
#HW 5
#Authors Weixi Ma and Kexin Huang


from makeWebsite import *   
import unittest  

class testmakeWebsite(unittest.TestCase):

    def test_safeOpen(self):
        self.assertTrue(safeOpen('i do not exist.txt') == True)
        self.assertTrue('Kexin Huang' in safeOpen('resumetest.txt'))
        self.assertTrue('Projects' in safeOpen('resumetest.txt'))


    def setUp(self):
        self.listFile = safeOpen('resumetest.txt')
        
    def test_detect_name(self):
        name = detect_name(self.listFile)
        self.assertEqual(name, 'Kexin Huang')
        self.assertRaises(ValueError, detect_name, ['aaaaaa'])

    def test_detect_emails(self):
        email = detect_emails(self.listFile)
        self.assertEqual(email, 'kexin@seas.upenn.edu') #test '.org'/'Capital'

    def test_detect_courses(self):
        courses = detect_courses(self.listFile)
        self.assertEqual(courses, 'Programming Cit590') #test punctuations like- +_)(:{ should be excluded

    def test_detect_projects(self):
        projects = detect_projects(self.listFile)
        self.assertEqual(projects,['CancerDetector.com, New Jersey, USA - Project manager', '', 'Biomedical Imaging - Developed a semi-automatic image'])
        
    def test_detect_education(self):
        education = detect_education(self.listFile)
        self.assertEqual(education,['University of P, Master of Eng', 'N University, - Bachelor of Science'])
        
    def testsurround_block(self):
        #check whether have the desired result
        text = surround_block('<div>', 'apple')
        self.assertEqual(text, '<div>|apple|</div>')
        text = surround_block('<h1>', 'lol')
        self.assertEqual(text, '<h1>|lol|</h1>')
        text = surround_block('<p>', 'ps')
        self.assertEqual(text, '<p>|ps|</p>')

    def testwrite_intro(self):
        f = open('test.txt', 'r+')
        write_intro('Eden Mark', 'ed123@gmail.com', f)
        #sets the file's current position at the beginning
        f.seek(0)
        lines = []
        for line in f:
            lines.append(line)
        #check whether have the desired result
        self.assertEqual(lines, ['<div>\n', '<h1>\n', 'Eden Mark\n', '</h1>\n', '<p>\n', 'ed123@gmail.com\n', '</p>\n', '</div>\n'])
        #reset the file and close it
        f.seek(0)
        f.truncate()
        f.close

    def testwrite_degrees(self):
        f = open('test.txt', 'r+')
        write_degrees(['Master in Java', 'Bachelor in Python'], f)
        #sets the file's current position at the beginning
        f.seek(0)
        lines = []
        for line in f:
            lines.append(line)
        #check whether have the desired result
        self.assertEqual(lines, ['<div>\n', '<h2>\n', 'Education\n', '</h2>\n', '<ul>\n', '<li>\n', 'Master in Java\n', '</li>\n', '<li>\n', 'Bachelor in Python\n', '</li>\n', '</ul>\n', '</div>\n'])
        #reset the file and close it
        f.seek(0)
        f.truncate()
        f.close
    
    def testwrite_projects(self):
        f = open('test.txt', 'r+')
        write_projects(['project A - blabla', 'project B - do something'], f)
        #sets the file's current position at the beginning
        f.seek(0)
        lines = []
        for line in f:
            lines.append(line)
        #check whether have the desired result
        self.assertEqual(lines, ['<div>\n', '<h2>\n', 'Projects\n', '</h2>\n', '<ul>\n', '<li>\n', '<p>\n', 'project A - blabla\n', '</p>\n', '</li>\n', '<li>\n', '<p>\n', 'project B - do something\n', '</p>\n', '</li>\n', '</ul>\n', '</div>\n'])
        #reset the file and close it
        f.seek(0)
        f.truncate()
        f.close

    def testwrite_courses(self):
        f = open('test.txt', 'r+')
        write_courses('cit590, mse536, ese521', f)
        #sets the file's current position at the beginning
        f.seek(0)
        lines = []
        for line in f:
            lines.append(line)
        #check whether have the desired result
        self.assertEqual(lines, ['<div>\n', '<h3>\n', 'Courses\n', '</h3>\n', '<span>\n', 'cit590, mse536, ese521\n', '</span>\n', '</div>\n'])
        #reset the file and close it
        f.seek(0)
        f.truncate()
        f.close

    def test_write_begin(self):
        f = open('test.txt', 'r+')
        write_begin(f)
        #sets the file's current position at the beginning
        f.seek(0)
        lines = []
        for line in f:
            lines.append(line)
        #check whether have the desired result
        self.assertEqual(lines, ['<div id="page-wrap">\n'])
        #reset the file and close it
        f.seek(0)
        f.truncate()
        f.close

    def test_write_end(self):
        f = open('test.txt', 'r+')
        write_end(f)
        #sets the file's current position at the beginning
        f.seek(0)
        lines = []
        for line in f:
            lines.append(line)
        #check whether have the desired result
        self.assertEqual(lines, ['</div>\n', '</body>\n', '</html>'])
        #reset the file and close it
        f.seek(0)
        f.truncate()
        f.close

unittest.main()


