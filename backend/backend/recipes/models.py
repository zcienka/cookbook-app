from django.db import models
import uuid


class Recipe(models.Model):
    id = models.UUIDField(default=uuid.uuid4, primary_key=True, null=False, editable=False)
    title = models.TextField(null=True, blank=True)
    description = models.TextField(null=True, blank=True)
    preparation_time = models.TextField(null=True, blank=True)
    calories = models.PositiveIntegerField(default=0)
    ingredients = models.TextField(null=True, blank=True)

    # class Meta:
    #     ordering = ('-date',)
