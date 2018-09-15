<img src="http://pages.di.unipi.it/ponza/public/images/salie/logo.png" width="200">




SalIE - Salient Open Information Extraction
============================================


This repository hosts a refactored and cleaned version of SalIE, the first framework addressing the extraction of *salient open facts* from arbitrary text.



Building
--------


**Requirements.** For running SalIE out-of-the-box, your machine needs only to have [Scala](https://www.scala-lang.org/download/) and [sbt](https://www.scala-sbt.org/) preinstalled.


**Setting-up.** Download this repository recursively, apply the patch and downlaod the pre-crafted embeddings from [link/to/embeddings](http://link/to/embeddings) with:

    git clone --recursive https://github.com/mponza/SalIE
    cd SalIE
    bash src/main/bash/patch.sh
    bash src/main/download-resources.sh
    
that will be properely collocated into `data/embeddings` directory.


**Running.** Given a data collection stored in `path/to/input/data` folder, where each element is a text file, you can extract its
salient open facts with SalIE by typing:

    src/main/bash/salient-extraction.sh path/to/input/data path/to/output/data
    
where `path/to/output/data` is the folder on which the salient open facts will be stored in the following JSON format:

    {
        "docID":         string      document ID
        "text":          string      content of the document
        "openfacts":     list        list of salient open facts, sorted by descending salience score
                    [
                        {
                            "text":         string      text of the open fact
                            "salience":     float       salience score   
                        }
                    ]          
    }


**Using SalIE within your Code.** An Example is provied in `src/main/scala/de/mpg/mpi/runners/RunExample.scala`.
The code has been developed on the top of [DkPro](https://dkpro.github.io/)/[UIMA](https://uima.apache.org/) frameworks, for more information, please check the documentation in their official websites.




Embeddings Wikipedia Open Facts via GloVe and Data Compression
---------------------------------------------------------------

We describe here the procedure used for generating the embedding vectors from the set of open facts extracted from the whole Wikipedia.


**Setting-up.** After you have clone this repository with `--recursive` option you have to install and create a 
[virtualenv](https://docs.python-guide.org/dev/virtualenvs/) environment in the `venv` directory:

    virtualenv venv
    
and install the Python requirements:

    source venv/bin/activate
    pip install -r src/main/python/requirements.txt


**Embeddings Generation &#38; Compression.** Given a file of open facts in JSON format (e.g., `path/to/agg-wikipedia.json`, 
see [this](#Dataset of Wikipedia Open Facts) section for the description of the format), the  output embeddings file (e.g., `path/to/agg-wikipedia.glove`) can be generated from scratch with:

    bash src/main/bash/facts2glove.sh path/to/agg-wikipedia.json path/to/agg-wikipedia.glove
    
In this example, the output filename will be `path/to/agg-wikipedia.glove.bin`. For setting up different GloVe parameters check `src/main/bash/facts2glove.sh`.



Evaluation
----------

If you want to evaluate the performance of SalIE on a data collection, just set-up and run the following steps.


**Setting-up.** After the creation and activation of your [virtualenv](https://docs.python-guide.org/dev/virtualenvs/) environment, you need to install the Python requirements:

    pip install -r src/main/python/requirements.txt


Then, you have to configure [pyrouge](https://pypi.org/project/pyrouge/) by creating the file `~/.pyrouge/settings.ini`
with the content:

    [pyrouge settings]
    home_dir = path/to/src/main/python/summarization/tools/ROUGE-1.5.5/
    
    
**Evaluation.** For evaluating a set of extracted salient facts with respect to documents' abstracts you need to run:

    python src/main/python/summarization evaluate path/to/open/facts/dir path/to/abstracts path/to/scores.json
    
where `path/to/open/facts/dir` is the path to a directory of a set of documents, each one with the following JSON format:


    {
       "docID":        string      id of the document
       "text":         string      content of the document
       "abstract":     string      abstract of the document

       "openfacts":    list        list of open facts

                   [
                       {
                           "text":        string      text of the open fact
                           "salience":    float       salience score
                       }
                   ]
    }
    
and the `path/to/abstracts` is the path to a directory of a set of documents, each one containing the document's abstract.


**Known Error (and How to Fix).** Running ROUGE can raise the "Cannot open exception db file for reading" exception. For fix it, just type:

    bash src/main/fixROUGE.sh
    
and then re-run the evaluation script.


**NYT Data Collection.** This dataset can be bought from [LDC](https://catalog.ldc.upenn.edu/ldc2008t19), while the document IDs used in our testbed can be downloaded [here](https://groviera1.di.unipi.it:5001/sharing/a4BAg9gf4).




Dataset of Wikipedia Open Facts
-------------------------------

You can download the Wikipedia dataset (dump of August 2017) containing the whole set of extracted open facts with different MinIE's modes (i.e., [aggressive](https://groviera1.di.unipi.it:5001/sharing/cOjx6zNww), [safe](https://groviera1.di.unipi.it:5001/sharing/qhbO2EVZQ), [dictionary](https://groviera1.di.unipi.it:5001/sharing/qJ37mLhXz) and [complete](https://groviera1.di.unipi.it:5001/sharing/yDLeBdm5t)).
Each file size is about 9GB (compressed) and each line is a Wikipedia page with the following JSON format:

    {
        "wikiID":       string      id of the Wikipedia page
        "text":         string      raw text of the Wikipedia page
        
        "sentences":    list        list of sentences containing the extracted open facts
        
                [
                    {
                        "text":     string      sentence text
                        "begin":    int         begin character offset of the sentence in the Wikipedia text
                        "end":      int         end character offset of the sentence in the Wikipedia text
                        
                        "openFacts":    list    list of open facts extracted from the sentence (warning: F of facts is uppercase here!)
                                    [
                                        {
                                            "subject":
                                                {
                                                    "text":     string      text of the subject
                                                    "head":     string      head of the subject
                                                    "begin":    int         begin character offset of the subject
                                                    "end":      int         end character offset of the subject
                                                }
                                                
                                            "relation":     same object structure of subject
                                            "object":       same object structure of subject
                                        }
                                    ]
                    }
                ]
    }





Citation and Further Reading
----------------------------

If you find any resource (code or data) of this repository useful, please cite our paper:

> [Marco Ponza](http://pages.di.unipi.it/ponza), [Luciano Del Corro](https://people.mpi-inf.mpg.de/~corrogg/), [Gerhard Waikum](http://people.mpi-inf.mpg.de/~weikum/) <br />
> Facts That Matter <br />
> *In Proceedings of the 2018 Conference of Empirical Methods in Natural Language Processing (EMNLP 2018)*



License
-------
The code in this repository has been released under GNU General Public License v.30.


