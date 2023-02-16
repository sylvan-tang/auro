from setuptools_scm import get_version
from os.path import *

version_prefix = "0.2.1.dev"

if __name__ == "__main__":
    version_path = dirname(abspath(__file__)) + '/scm_version.toml'
    with open(version_path, mode='w') as fw:
        version = version_prefix + str(hash(get_version()) % 10000000)
        fw.write(f"__version__=\"{version}\"")
