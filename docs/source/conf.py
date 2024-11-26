# Configuration file for the Sphinx documentation builder.
#
# For the full list of built-in configuration values, see the documentation:
# https://www.sphinx-doc.org/en/master/usage/configuration.html

# -- Project information -----------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#project-information

project = ""
copyright = '2024, Christian Oliverio'
author = 'Christian Oliverio'
# release = '0.1.0'

# -- General configuration ---------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#general-configuration

extensions = [
    "sphinx.ext.autodoc",
    "myst_parser",
    "sphinx_inline_tabs"
]

sphinx_tabs_valid_builders = ['linkcheck']

# the COLOR for our team is HEX: #041589 or HEX: #001489

source_suffix = ['.rst', '.md']

templates_path = ['_templates']
exclude_patterns = []

html_favicon = "ico/robovikes.ico"

# -- Options for HTML output -------------------------------------------------
# https://www.sphinx-doc.org/en/master/usage/configuration.html#options-for-html-output

html_theme = 'furo'
html_title = ""
html_static_path = ['_static']

html_css_files = [
    "theme_robovikes.css"
]

html_theme_options = {
    "light_logo": "images/robovikes-light.png",
    "dark_logo": "images/robovikes-dark.png",
    "sidebar_hide_name": True,
}