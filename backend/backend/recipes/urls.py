from django.urls import path
from . import views

urlpatterns = [
    path('', views.recipe_list_create_view, name='recipes'),
    path('category', views.recipe_by_category_view, name='category'),
]
