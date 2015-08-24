import boto
from boto.s3.connection import S3Connection
from boto.s3.key import Key

def putClip(profileName, bucketName, fileName, key):
    #os.environ['S3_USE_SIGV4'] = 'True'
    c = S3Connection(profile_name=profileName,is_secure=True,host='s3.eu-central-1.amazonaws.com')
    b = c.get_bucket(bucketName)
    k = Key(b)
    k.key = key
    k.set_contents_from_filename(fileName)
    url = k.generate_url(expires_in = 60*60, force_http = True).split("?")[0]
    return url;

#putClip('komar-clips', "a.mp3", "exampleKey")
