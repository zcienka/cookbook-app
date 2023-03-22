from .models import Recipe
from .serializers import RecipeSerializer
from rest_framework import generics


class RecipeListCreateAPIView(generics.ListCreateAPIView):
    queryset = Recipe.objects.all()
    serializer_class = RecipeSerializer


recipe_list_create_view = RecipeListCreateAPIView.as_view()