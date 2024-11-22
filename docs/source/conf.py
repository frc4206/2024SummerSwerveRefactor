# Configuration file for the Sphinx documentation builder.
#
# For the full list of built-in configuration values, see the documentation:
# https://www.sphinx-doc.org/en/master/usage/configuration.html

# -- Project information -----------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#project-information

project = 'battleaid-example'
copyright = '2024, Christian Oliverio'
author = 'Christian Oliverio'
release = '0.1.0'

# -- General configuration ---------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#general-configuration

extensions = [
    'sphinx.ext.autodoc'    
]

source_suffix = ['.rst', '.md']

templates_path = ['_templates']
exclude_patterns = []

def setup(app):
    app.add_css_file("css/theme_ctre.css")

# -- Options for HTML output -------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#options-for-html-output

html_theme = 'furo'
html_title = "this is my test title"
html_static_path = ['_static']

html_theme_options = {
    "light_logo": "images/robovikes-light.png",
    "dark_logo": "images/robovikes-dark.png",
}