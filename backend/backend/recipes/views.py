from .models import Recipe
from .serializers import RecipeSerializer
from rest_framework import generics, response
from django.conf import settings
from django.urls import reverse


class RecipeListCreateAPIView(generics.ListCreateAPIView):
    serializer_class = RecipeSerializer

    def get_queryset(self):
        category = self.request.GET.get('category')
        queryset = Recipe.objects.all()

        if category:
            queryset = queryset.filter(category=category)

        return queryset


recipe_list_create_view = RecipeListCreateAPIView.as_view()


class RecipeByCategoryAPIView(generics.ListAPIView):
    serializer_class = RecipeSerializer

    def get_queryset(self):
        queryset = Recipe.objects.values_list('category', flat=True).distinct()
        return queryset

    def list(self, request, *args, **kwargs):
        queryset = self.get_queryset()
        media_url = settings.MEDIA_URL

        categories = []
        for category in queryset:
            category_data = {
                'name': category,
                'url': media_url + f'category/{category}.jpg'
            }
            categories.append(category_data)

        return response.Response(categories)


recipe_by_category_view = RecipeByCategoryAPIView.as_view()
