from django.db import models
import uuid


class Recipe(models.Model):
    id = models.UUIDField(default=uuid.uuid4, primary_key=True, null=False, editable=False)
    title = models.TextField(null=True, blank=True)
    description = models.TextField(null=True, blank=True)
    preparation_time = models.PositiveIntegerField(default=0)
    calories = models.PositiveIntegerField(default=0)
    ingredients = models.TextField(null=True, blank=True)
    image = models.TextField(null=True, blank=True)
    category = models.TextField(null=True, blank=True)
    recipe = models.TextField(null=True, blank=True)
